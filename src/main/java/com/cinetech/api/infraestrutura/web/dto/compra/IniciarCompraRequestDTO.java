package com.cinetech.api.infraestrutura.web.dto.compra;

import jakarta.validation.constraints.NotBlank;

public class IniciarCompraRequestDTO {
    @NotBlank(message = "ID do cliente não pode ser vazio.")
    private String clienteId;
    @NotBlank(message = "ID da sessão não pode ser vazio.")
    private String sessaoId;
    @NotBlank(message = "Identificador do assento não pode ser vazio.")
    private String identificadorAssento;
    private String codigoPromocional;

    // Getters e Setters
    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }
    public String getSessaoId() { return sessaoId; }
    public void setSessaoId(String sessaoId) { this.sessaoId = sessaoId; }
    public String getIdentificadorAssento() { return identificadorAssento; }
    public void setIdentificadorAssento(String identificadorAssento) { this.identificadorAssento = identificadorAssento; }
    public String getCodigoPromocional() { return codigoPromocional; }
    public void setCodigoPromocional(String codigoPromocional) { this.codigoPromocional = codigoPromocional; }
}
