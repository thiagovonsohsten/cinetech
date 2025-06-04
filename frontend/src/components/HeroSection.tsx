
import { useState, useEffect } from 'react';
import { Play, Star, Clock } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';

const featuredMovies = [
  {
    id: '1',
    title: 'A Origem',
    synopsis: 'Um ladrão habilidoso recebe uma chance de redenção se conseguir realizar a impossível tarefa de implantar uma ideia no subconsciente de um alvo.',
    rating: 4.8,
    duration: 148,
    ageRating: '12 anos',
    backgroundImage: 'https://images.unsplash.com/photo-1489599017127-e0b6b3ec5e8d?w=1920&h=1080&fit=crop',
    genre: ['Ficção Científica', 'Suspense']
  },
  {
    id: '2',
    title: 'Batman: O Cavaleiro das Trevas',
    synopsis: 'Batman enfrenta seus maiores testes psicológicos e físicos quando a ameaça conhecida como Coringa causa estragos em Gotham City.',
    rating: 4.9,
    duration: 152,
    ageRating: '12 anos',
    backgroundImage: 'https://images.unsplash.com/photo-1518676590629-3dcbd9c5a5c9?w=1920&h=1080&fit=crop',
    genre: ['Ação', 'Crime']
  },
  {
    id: '3',
    title: 'Interestelar',
    synopsis: 'Uma equipe de exploradores viaja através de um buraco de minhoca no espaço na tentativa de garantir a sobrevivência da humanidade.',
    rating: 4.7,
    duration: 169,
    ageRating: '10 anos',
    backgroundImage: 'https://images.unsplash.com/photo-1446776877081-d282a0f896e2?w=1920&h=1080&fit=crop',
    genre: ['Ficção Científica', 'Drama']
  }
];

const HeroSection = () => {
  const [currentSlide, setCurrentSlide] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentSlide((prev) => (prev + 1) % featuredMovies.length);
    }, 5000);

    return () => clearInterval(timer);
  }, []);

  const currentMovie = featuredMovies[currentSlide];

  return (
    <section className="relative h-screen overflow-hidden">
      {/* Background Image with Overlay */}
      <div 
        className="absolute inset-0 bg-cover bg-center transition-all duration-1000"
        style={{ backgroundImage: `url(${currentMovie.backgroundImage})` }}
      />
      <div className="absolute inset-0 bg-gradient-to-r from-black/80 via-black/50 to-transparent" />
      
      {/* Content */}
      <div className="relative z-10 h-full flex items-center">
        <div className="container mx-auto px-4">
          <div className="max-w-2xl">
            <div className="animate-fade-in">
              <div className="flex items-center space-x-4 mb-4">
                <Badge variant="secondary" className="bg-secondary text-white">
                  {currentMovie.ageRating}
                </Badge>
                <div className="flex items-center space-x-2">
                  {currentMovie.genre.map((genre) => (
                    <Badge key={genre} variant="outline" className="border-white/30 text-white">
                      {genre}
                    </Badge>
                  ))}
                </div>
              </div>
              
              <h1 className="text-5xl md:text-7xl font-heading font-bold text-white mb-6 leading-tight">
                {currentMovie.title}
              </h1>
              
              <div className="flex items-center space-x-6 mb-6 text-white">
                <div className="flex items-center space-x-2">
                  <Star className="w-5 h-5 text-yellow-400 fill-current" />
                  <span className="text-lg font-medium">{currentMovie.rating}</span>
                </div>
                <div className="flex items-center space-x-2">
                  <Clock className="w-5 h-5" />
                  <span>{currentMovie.duration} min</span>
                </div>
              </div>
              
              <p className="text-xl text-gray-200 mb-8 leading-relaxed max-w-xl">
                {currentMovie.synopsis}
              </p>
              
              <div className="flex flex-col sm:flex-row gap-4">
                <Button size="lg" className="bg-primary hover:bg-primary/90 text-white px-8 py-4 text-lg glow-effect">
                  Comprar Ingressos
                </Button>
                <Button size="lg" variant="outline" className="border-white/30 text-white hover:bg-white/10 px-8 py-4 text-lg">
                  <Play className="w-5 h-5 mr-2" />
                  Assistir Trailer
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      {/* Slide Indicators */}
      <div className="absolute bottom-8 left-1/2 transform -translate-x-1/2 flex space-x-3">
        {featuredMovies.map((_, index) => (
          <button
            key={index}
            onClick={() => setCurrentSlide(index)}
            className={`w-3 h-3 rounded-full transition-all duration-300 ${
              index === currentSlide ? 'bg-primary w-8' : 'bg-white/30 hover:bg-white/50'
            }`}
          />
        ))}
      </div>
    </section>
  );
};

export default HeroSection;
