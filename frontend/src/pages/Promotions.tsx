
import { useState } from 'react';
import { Tag, Clock, Star, Gift, Percent, Calendar } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import Header from '@/components/Header';
import Footer from '@/components/Footer';

interface PromotionData {
  id: string;
  title: string;
  description: string;
  discountPercentage: number;
  validUntil: string;
  imageUrl: string;
  type: 'desconto' | 'fidelidade' | 'especial';
  conditions: string[];
  isActive: boolean;
}

const samplePromotions: PromotionData[] = [
  {
    id: '1',
    title: 'Estudante Paga Meia',
    description: 'Estudantes com carteirinha válida pagam 50% do valor do ingresso em todas as sessões.',
    discountPercentage: 50,
    validUntil: '2024-12-31',
    imageUrl: 'https://images.unsplash.com/photo-1523240795612-9a054b0db644?w=600&h=400&fit=crop',
    type: 'desconto',
    conditions: [
      'Apresentar carteirinha de estudante válida',
      'Válido para filmes 2D e 3D',
      'Não cumulativo com outras promoções'
    ],
    isActive: true
  },
  {
    id: '2',
    title: 'Terça-feira é Dia de Cinema',
    description: 'Todos os ingressos pela metade do preço nas terças-feiras para sessões até 18h.',
    discountPercentage: 50,
    validUntil: '2024-12-31',
    imageUrl: 'https://images.unsplash.com/photo-1489599017127-e0b6b3ec5e8d?w=600&h=400&fit=crop',
    type: 'especial',
    conditions: [
      'Válido apenas às terças-feiras',
      'Sessões até 18h',
      'Sujeito à disponibilidade'
    ],
    isActive: true
  },
  {
    id: '3',
    title: 'Programa Fidelidade Cinetech',
    description: 'Acumule pontos a cada compra e troque por ingressos gratuitos. A cada R$ 10 gastos, ganhe 1 ponto.',
    discountPercentage: 0,
    validUntil: '2024-12-31',
    imageUrl: 'https://images.unsplash.com/photo-1616530940355-351fabd9524b?w=600&h=400&fit=crop',
    type: 'fidelidade',
    conditions: [
      '1 ponto = R$ 10 gastos',
      '100 pontos = 1 ingresso grátis',
      'Pontos expiram em 6 meses'
    ],
    isActive: true
  },
  {
    id: '4',
    title: 'Idosos e PCD - Meia Entrada',
    description: 'Idosos acima de 60 anos e pessoas com deficiência têm direito à meia entrada.',
    discountPercentage: 50,
    validUntil: '2024-12-31',
    imageUrl: 'https://images.unsplash.com/photo-1559027615-cd4628902d4a?w=600&h=400&fit=crop',
    type: 'desconto',
    conditions: [
      'Apresentar documento comprobatório',
      'Válido para acompanhante de PCD',
      'Todos os horários e salas'
    ],
    isActive: true
  },
  {
    id: '5',
    title: 'Combo Família - 4 Ingressos',
    description: 'Compre 4 ingressos e ganhe 30% de desconto no total. Ideal para famílias!',
    discountPercentage: 30,
    validUntil: '2024-08-31',
    imageUrl: 'https://images.unsplash.com/photo-1511895426328-dc8714191300?w=600&h=400&fit=crop',
    type: 'especial',
    conditions: [
      'Mínimo de 4 ingressos',
      'Mesma sessão',
      'Não válido em feriados'
    ],
    isActive: true
  },
  {
    id: '6',
    title: 'Sessão da Meia-Noite - 40% OFF',
    description: 'Sessões após 23h com desconto especial para os corajosos da madrugada.',
    discountPercentage: 40,
    validUntil: '2024-07-31',
    imageUrl: 'https://images.unsplash.com/photo-1440404653325-ab127d49abc1?w=600&h=400&fit=crop',
    type: 'especial',
    conditions: [
      'Válido apenas para sessões após 23h',
      'Sujeito à disponibilidade',
      'Não válido aos domingos'
    ],
    isActive: false
  }
];

const Promotions = () => {
  const [selectedType, setSelectedType] = useState('todas');

  const filteredPromotions = samplePromotions.filter(promotion => {
    if (selectedType === 'todas') return promotion.isActive;
    return promotion.type === selectedType && promotion.isActive;
  });

  const getTypeColor = (type: string) => {
    switch (type) {
      case 'desconto': return 'bg-blue-500';
      case 'fidelidade': return 'bg-purple-500';
      case 'especial': return 'bg-orange-500';
      default: return 'bg-gray-500';
    }
  };

  const getTypeLabel = (type: string) => {
    switch (type) {
      case 'desconto': return 'Desconto';
      case 'fidelidade': return 'Fidelidade';
      case 'especial': return 'Especial';
      default: return 'Promoção';
    }
  };

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR');
  };

  return (
    <div className="min-h-screen">
      <Header />
      <main className="bg-gradient-to-b from-cinema-dark to-cinema-darker">
        {/* Header Section */}
        <div className="bg-cinema-dark border-b border-white/10">
          <div className="container mx-auto px-4 py-8">
            <div className="text-center mb-8">
              <h1 className="text-4xl md:text-5xl font-heading font-bold text-white mb-4">
                Promoções
              </h1>
              <p className="text-xl text-gray-300 max-w-2xl mx-auto">
                Aproveite nossas ofertas especiais e economize na sua próxima sessão de cinema
              </p>
            </div>

            {/* Filter Tabs */}
            <div className="flex justify-center mb-8">
              <div className="glass-effect rounded-full p-1 flex flex-wrap">
                <Button
                  variant={selectedType === 'todas' ? 'default' : 'ghost'}
                  onClick={() => setSelectedType('todas')}
                  className={`px-6 py-2 rounded-full transition-all duration-300 ${
                    selectedType === 'todas' 
                      ? 'bg-primary text-white' 
                      : 'text-gray-300 hover:text-white'
                  }`}
                >
                  Todas
                </Button>
                <Button
                  variant={selectedType === 'desconto' ? 'default' : 'ghost'}
                  onClick={() => setSelectedType('desconto')}
                  className={`px-6 py-2 rounded-full transition-all duration-300 ${
                    selectedType === 'desconto' 
                      ? 'bg-primary text-white' 
                      : 'text-gray-300 hover:text-white'
                  }`}
                >
                  Descontos
                </Button>
                <Button
                  variant={selectedType === 'fidelidade' ? 'default' : 'ghost'}
                  onClick={() => setSelectedType('fidelidade')}
                  className={`px-6 py-2 rounded-full transition-all duration-300 ${
                    selectedType === 'fidelidade' 
                      ? 'bg-primary text-white' 
                      : 'text-gray-300 hover:text-white'
                  }`}
                >
                  Fidelidade
                </Button>
                <Button
                  variant={selectedType === 'especial' ? 'default' : 'ghost'}
                  onClick={() => setSelectedType('especial')}
                  className={`px-6 py-2 rounded-full transition-all duration-300 ${
                    selectedType === 'especial' 
                      ? 'bg-primary text-white' 
                      : 'text-gray-300 hover:text-white'
                  }`}
                >
                  Especiais
                </Button>
              </div>
            </div>
          </div>
        </div>

        {/* Promotions Grid */}
        <div className="container mx-auto px-4 py-12">
          <div className="mb-8">
            <h2 className="text-2xl font-heading font-bold text-white mb-2">
              {filteredPromotions.length} Promoções Ativas
            </h2>
            <p className="text-gray-300">
              Aproveite essas ofertas limitadas!
            </p>
          </div>

          {filteredPromotions.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {filteredPromotions.map((promotion) => (
                <Card key={promotion.id} className="bg-white/5 border-white/10 hover:bg-white/10 transition-all duration-300 overflow-hidden">
                  <div className="relative">
                    <img
                      src={promotion.imageUrl}
                      alt={promotion.title}
                      className="w-full h-48 object-cover"
                    />
                    <div className="absolute top-4 left-4">
                      <Badge className={`${getTypeColor(promotion.type)} text-white`}>
                        {getTypeLabel(promotion.type)}
                      </Badge>
                    </div>
                    {promotion.discountPercentage > 0 && (
                      <div className="absolute top-4 right-4">
                        <div className="bg-red-500 text-white rounded-full w-16 h-16 flex items-center justify-center">
                          <div className="text-center">
                            <div className="text-lg font-bold">{promotion.discountPercentage}%</div>
                            <div className="text-xs">OFF</div>
                          </div>
                        </div>
                      </div>
                    )}
                  </div>
                  
                  <CardHeader>
                    <CardTitle className="text-white text-lg">
                      {promotion.title}
                    </CardTitle>
                  </CardHeader>
                  
                  <CardContent className="space-y-4">
                    <p className="text-gray-300 text-sm leading-relaxed">
                      {promotion.description}
                    </p>

                    <div className="space-y-2">
                      <h4 className="text-white font-semibold text-sm flex items-center gap-2">
                        <Tag className="w-4 h-4" />
                        Condições:
                      </h4>
                      <ul className="text-gray-400 text-xs space-y-1">
                        {promotion.conditions.map((condition, index) => (
                          <li key={index} className="flex items-start gap-2">
                            <span className="text-primary">•</span>
                            {condition}
                          </li>
                        ))}
                      </ul>
                    </div>

                    <div className="flex items-center justify-between pt-4 border-t border-white/10">
                      <div className="flex items-center gap-2 text-gray-400 text-sm">
                        <Calendar className="w-4 h-4" />
                        Válido até {formatDate(promotion.validUntil)}
                      </div>
                      <Button size="sm" className="bg-primary hover:bg-primary/90 text-white">
                        Usar Agora
                      </Button>
                    </div>
                  </CardContent>
                </Card>
              ))}
            </div>
          ) : (
            <div className="text-center py-16">
              <div className="glass-effect rounded-xl p-8 max-w-md mx-auto">
                <Gift className="w-16 h-16 text-gray-400 mx-auto mb-4" />
                <h3 className="text-xl font-semibold text-white mb-2">Nenhuma promoção encontrada</h3>
                <p className="text-gray-300">Tente selecionar outro tipo de promoção.</p>
              </div>
            </div>
          )}
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default Promotions;
