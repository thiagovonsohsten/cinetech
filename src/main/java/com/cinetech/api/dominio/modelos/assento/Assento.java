package com.cinetech.api.dominio.modelos.assento;

import com.cinetech.api.dominio.enums.StatusAssento;
import com.cinetech.api.dominio.enums.TipoAssento;
import com.cinetech.api.dominio.modelos.cliente.ClienteId;
import com.cinetech.api.dominio.modelos.sessao.Sessao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public abstract class Assento {
    private final AssentoId id;
    private final Sessao sessao; // Referência à raiz do agregado. Assento não existe sem Sessao.
    private final String identificadorPosicao; // Ex: "A1", "C5" (imutável após criação)
    private final TipoAssento tipo; // comum, VIP, PCD [cite: 4]
    private StatusAssento status;
    private ClienteId clienteIdReservaTemporaria;
    private LocalDateTime timestampExpiracaoReserva;
    private String numero;
    private boolean ocupado;
    private double precoBase;

    public Assento(Sessao sessao, String identificadorPosicao, TipoAssento tipo) {
        this(AssentoId.novo(), sessao, identificadorPosicao, tipo, StatusAssento.DISPONIVEL, null, null);
    }

    public Assento(AssentoId id, Sessao sessao, String identificadorPosicao, TipoAssento tipo, StatusAssento status,
                   ClienteId clienteIdReservaTemporaria, LocalDateTime timestampExpiracaoReserva) {
        this.id = Objects.requireNonNull(id, "ID do Assento não pode ser nulo.");
        this.sessao = Objects.requireNonNull(sessao, "Sessão do assento não pode ser nula.");
        if (identificadorPosicao == null || identificadorPosicao.trim().isEmpty()) {
            throw new IllegalArgumentException("Identificador de posição do assento não pode ser vazio.");
        }
        this.identificadorPosicao = identificadorPosicao.trim();
        this.tipo = Objects.requireNonNull(tipo, "Tipo do assento não pode ser nulo.");
        this.status = Objects.requireNonNull(status, "Status do assento não pode ser nulo.");
        this.clienteIdReservaTemporaria = clienteIdReservaTemporaria;
        this.timestampExpiracaoReserva = timestampExpiracaoReserva;
        this.ocupado = false;
    }

    public AssentoId getId() { return id; }
    public Sessao getSessao() { return sessao; }
    public String getIdentificadorPosicao() { return identificadorPosicao; }
    public TipoAssento getTipo() { return tipo; }
    public StatusAssento getStatus() { return status; }
    public ClienteId getClienteIdReservaTemporaria() { return clienteIdReservaTemporaria; }
    public LocalDateTime getTimestampExpiracaoReserva() { return timestampExpiracaoReserva; }

    private boolean estaDisponivelParaReservaLogica(LocalDateTime agora) {
        if (this.status == StatusAssento.BLOQUEADO) return false;

        return this.status == StatusAssento.DISPONIVEL ||
                (this.status == StatusAssento.RESERVADO_TEMP && estaReservaTemporariaExpirada(agora));
    }

    private boolean estaReservaTemporariaExpirada(LocalDateTime agora) {
        Objects.requireNonNull(agora, "Data de referência para expiração não pode ser nula.");
        return this.timestampExpiracaoReserva != null && agora.isAfter(this.timestampExpiracaoReserva);
    }

    public void reservarTemporariamente(ClienteId clienteId, int minutosParaExpirar) {
        Objects.requireNonNull(clienteId, "ID do cliente não pode ser nulo para reserva temporária.");
        if (minutosParaExpirar <= 0) {
            throw new IllegalArgumentException("Tempo de expiração da reserva deve ser positivo.");
        }

        if (!estaDisponivelParaReservaLogica(LocalDateTime.now())) {
            throw new IllegalStateException("Assento '" + identificadorPosicao + "' na sessão " + sessao.getId() +
                    " não está disponível para reserva (status atual: " + this.status + ").");
        }

        this.status = StatusAssento.RESERVADO_TEMP;
        this.clienteIdReservaTemporaria = clienteId;
        this.timestampExpiracaoReserva = LocalDateTime.now().plusMinutes(minutosParaExpirar);
    }

    public void confirmarOcupacaoDefinitiva() {
        if (this.status == StatusAssento.OCUPADO_FINAL) {
            System.out.println("WARN DOMINIO: Assento " + identificadorPosicao + " da sessão " + sessao.getId() + " já está OCUPADO_FINAL.");
            return;
        }
        if (this.status == StatusAssento.BLOQUEADO) {
            throw new IllegalStateException("Assento '" + identificadorPosicao + "' está BLOQUEADO e não pode ser ocupado.");
        }
        this.status = StatusAssento.OCUPADO_FINAL;
        this.clienteIdReservaTemporaria = null;
        this.timestampExpiracaoReserva = null;
    }

    public void liberar() {
        if (this.status == StatusAssento.RESERVADO_TEMP || this.status == StatusAssento.OCUPADO_FINAL) {
            this.status = StatusAssento.DISPONIVEL;
            this.clienteIdReservaTemporaria = null;
            this.timestampExpiracaoReserva = null;
        } else if (this.status == StatusAssento.DISPONIVEL) {
            // Já está disponível, não faz nada.
        } else if (this.status == StatusAssento.BLOQUEADO) {
            throw new IllegalStateException("Assento '" + identificadorPosicao + "' está BLOQUEADO e não pode ser liberado por esta operação.");
        }
    }

    public boolean liberarSeReservaTemporariaExpirada(LocalDateTime agora) {
        Objects.requireNonNull(agora, "Data de referência para expiração não pode ser nula.");
        if (this.status == StatusAssento.RESERVADO_TEMP && estaReservaTemporariaExpirada(agora)) {
            liberar();
            return true;
        }
        return false;
    }

    public void bloquearAdministrativamente() {
        this.status = StatusAssento.BLOQUEADO;
        this.clienteIdReservaTemporaria = null;
        this.timestampExpiracaoReserva = null;
    }

    public void desbloquearAdministrativamente() {
        if (this.status != StatusAssento.BLOQUEADO) {
            throw new IllegalStateException("Assento '" + identificadorPosicao + "' não pode ser desbloqueado pois não está BLOQUEADO. Status atual: " + this.status);
        }
        liberar();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assento assento = (Assento) o;
        return id.equals(assento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Assento{" +
                "id=" + id +
                ", identificadorPosicao='" + identificadorPosicao + '\'' +
                ", tipo=" + tipo +
                ", status=" + status +
                ", sessaoId=" + (sessao != null ? sessao.getId() : "N/A") +
                '}';
    }

    public abstract double getPreco();
}
