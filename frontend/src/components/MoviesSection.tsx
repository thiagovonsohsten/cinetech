
import { useState } from 'react';
import { Button } from '@/components/ui/button';
import MovieCard from './MovieCard';
import { Movie } from '@/types';

const sampleMovies: Movie[] = [
  {
    id: '1',
    title: 'A Origem',
    genre: ['Ficção Científica', 'Suspense'],
    ageRating: '12 anos',
    language: 'Português',
    duration: 148,
    rating: 4.8,
    synopsis: 'Um ladrão habilidoso recebe uma chance de redenção se conseguir realizar a impossível tarefa de implantar uma ideia no subconsciente de um alvo.',
    posterUrl: 'https://images.unsplash.com/photo-1489599017127-e0b6b3ec5e8d?w=400&h=600&fit=crop',
    cast: ['Leonardo DiCaprio', 'Marion Cotillard', 'Tom Hardy'],
    director: 'Christopher Nolan',
    releaseDate: '2010-07-16',
    featured: true
  },
  {
    id: '2',
    title: 'Batman: O Cavaleiro das Trevas',
    genre: ['Ação', 'Crime', 'Drama'],
    ageRating: '12 anos',
    language: 'Português',
    duration: 152,
    rating: 4.9,
    synopsis: 'Batman enfrenta seus maiores testes psicológicos e físicos quando a ameaça conhecida como Coringa causa estragos em Gotham City.',
    posterUrl: 'https://images.unsplash.com/photo-1518676590629-3dcbd9c5a5c9?w=400&h=600&fit=crop',
    cast: ['Christian Bale', 'Heath Ledger', 'Aaron Eckhart'],
    director: 'Christopher Nolan',
    releaseDate: '2008-07-18'
  },
  {
    id: '3',
    title: 'Interestelar',
    genre: ['Ficção Científica', 'Drama'],
    ageRating: '10 anos',
    language: 'Português',
    duration: 169,
    rating: 4.7,
    synopsis: 'Uma equipe de exploradores viaja através de um buraco de minhoca no espaço na tentativa de garantir a sobrevivência da humanidade.',
    posterUrl: 'https://images.unsplash.com/photo-1446776877081-d282a0f896e2?w=400&h=600&fit=crop',
    cast: ['Matthew McConaughey', 'Anne Hathaway', 'Jessica Chastain'],
    director: 'Christopher Nolan',
    releaseDate: '2014-11-07'
  },
  {
    id: '4',
    title: 'Vingadores: Ultimato',
    genre: ['Ação', 'Aventura', 'Ficção Científica'],
    ageRating: '12 anos',
    language: 'Português',
    duration: 181,
    rating: 4.6,
    synopsis: 'Os Vingadores se reúnem mais uma vez para reverter os danos causados por Thanos e restaurar o equilíbrio do universo.',
    posterUrl: 'https://images.unsplash.com/photo-1635805737707-575885ab0820?w=400&h=600&fit=crop',
    cast: ['Robert Downey Jr.', 'Chris Evans', 'Scarlett Johansson'],
    director: 'Anthony Russo, Joe Russo',
    releaseDate: '2019-04-26'
  },
  {
    id: '5',
    title: 'Duna',
    genre: ['Ficção Científica', 'Aventura'],
    ageRating: '12 anos',
    language: 'Português',
    duration: 155,
    rating: 4.5,
    synopsis: 'Paul Atreides lidera tribos nômades em uma batalha para controlar o planeta deserto Arrakis.',
    posterUrl: 'https://images.unsplash.com/photo-1598387993441-a364f854c3e1?w=400&h=600&fit=crop',
    cast: ['Timothée Chalamet', 'Rebecca Ferguson', 'Oscar Isaac'],
    director: 'Denis Villeneuve',
    releaseDate: '2021-10-22'
  },
  {
    id: '6',
    title: 'Top Gun: Maverick',
    genre: ['Ação', 'Drama'],
    ageRating: '12 anos',
    language: 'Português',
    duration: 130,
    rating: 4.7,
    synopsis: 'Após trinta anos, Maverick ainda está ultrapassando os limites como um dos principais aviadores navais.',
    posterUrl: 'https://images.unsplash.com/photo-1570610401277-4accafff80a2?w=400&h=600&fit=crop',
    cast: ['Tom Cruise', 'Miles Teller', 'Jennifer Connelly'],
    director: 'Joseph Kosinski',
    releaseDate: '2022-05-27'
  }
];

const MoviesSection = () => {
  const [activeTab, setActiveTab] = useState<'now-showing' | 'coming-soon'>('now-showing');

  const nowShowingMovies = sampleMovies.slice(0, 4);
  const comingSoonMovies = sampleMovies.slice(4, 6);

  return (
    <section className="py-16 bg-gradient-to-b from-cinema-dark to-cinema-darker">
      <div className="container mx-auto px-4">
        <div className="text-center mb-12">
          <h2 className="text-4xl md:text-5xl font-heading font-bold text-white mb-4">
            Descubra Filmes
          </h2>
          <p className="text-xl text-gray-300 max-w-2xl mx-auto">
            Experimente os últimos lançamentos e clássicos atemporais em nossas salas premium
          </p>
        </div>

        {/* Tabs */}
        <div className="flex justify-center mb-12">
          <div className="glass-effect rounded-full p-1 flex">
            <Button
              variant={activeTab === 'now-showing' ? 'default' : 'ghost'}
              onClick={() => setActiveTab('now-showing')}
              className={`px-8 py-3 rounded-full transition-all duration-300 ${
                activeTab === 'now-showing' 
                  ? 'bg-primary text-white' 
                  : 'text-gray-300 hover:text-white'
              }`}
            >
              Em Cartaz
            </Button>
            <Button
              variant={activeTab === 'coming-soon' ? 'default' : 'ghost'}
              onClick={() => setActiveTab('coming-soon')}
              className={`px-8 py-3 rounded-full transition-all duration-300 ${
                activeTab === 'coming-soon' 
                  ? 'bg-primary text-white' 
                  : 'text-gray-300 hover:text-white'
              }`}
            >
              Em Breve
            </Button>
          </div>
        </div>

        {/* Movie Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6 mb-12">
          {(activeTab === 'now-showing' ? nowShowingMovies : comingSoonMovies).map((movie) => (
            <div key={movie.id} className="animate-fade-in">
              <MovieCard movie={movie} />
            </div>
          ))}
        </div>

        {/* View All Button */}
        <div className="text-center">
          <Button size="lg" variant="outline" className="border-white/30 text-white hover:bg-white/10 px-8 py-4">
            Ver Todos os Filmes
          </Button>
        </div>
      </div>
    </section>
  );
};

export default MoviesSection;
