
import { useState } from 'react';
import { Search, Filter, Star, Clock, Calendar } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Badge } from '@/components/ui/badge';
import Header from '@/components/Header';
import Footer from '@/components/Footer';
import MovieCard from '@/components/MovieCard';
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

const Movies = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedGenre, setSelectedGenre] = useState('todos');
  const [selectedRating, setSelectedRating] = useState('todos');
  const [selectedLanguage, setSelectedLanguage] = useState('todos');

  const genres = ['Ação', 'Aventura', 'Ficção Científica', 'Drama', 'Crime', 'Suspense'];
  const ratings = ['Livre', '10 anos', '12 anos', '14 anos', '16 anos', '18 anos'];
  const languages = ['Português', 'Inglês', 'Espanhol'];

  const filteredMovies = sampleMovies.filter(movie => {
    const matchesSearch = movie.title.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesGenre = selectedGenre === 'todos' || movie.genre.includes(selectedGenre);
    const matchesRating = selectedRating === 'todos' || movie.ageRating === selectedRating;
    const matchesLanguage = selectedLanguage === 'todos' || movie.language === selectedLanguage;
    
    return matchesSearch && matchesGenre && matchesRating && matchesLanguage;
  });

  return (
    <div className="min-h-screen">
      <Header />
      <main className="bg-gradient-to-b from-cinema-dark to-cinema-darker">
        {/* Header Section */}
        <div className="bg-cinema-dark border-b border-white/10">
          <div className="container mx-auto px-4 py-8">
            <div className="text-center mb-8">
              <h1 className="text-4xl md:text-5xl font-heading font-bold text-white mb-4">
                Filmes em Cartaz
              </h1>
              <p className="text-xl text-gray-300 max-w-2xl mx-auto">
                Descubra nossa coleção completa de filmes atualmente em exibição e em breve
              </p>
            </div>

            {/* Search Bar */}
            <div className="max-w-md mx-auto mb-8">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
                <Input
                  type="text"
                  placeholder="Buscar filmes..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="pl-10 bg-white/10 border-white/20 text-white placeholder-gray-400"
                />
              </div>
            </div>

            {/* Filters */}
            <div className="glass-effect rounded-xl p-6">
              <div className="flex items-center gap-2 mb-4">
                <Filter className="w-5 h-5 text-primary" />
                <h3 className="text-lg font-semibold text-white">Filtros</h3>
              </div>
              
              <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                {/* Genre Filter */}
                <div>
                  <label className="block text-sm font-medium text-gray-300 mb-2">Gênero</label>
                  <select
                    value={selectedGenre}
                    onChange={(e) => setSelectedGenre(e.target.value)}
                    className="w-full bg-white/10 border border-white/20 rounded-md px-3 py-2 text-white"
                  >
                    <option value="todos">Todos os Gêneros</option>
                    {genres.map(genre => (
                      <option key={genre} value={genre}>{genre}</option>
                    ))}
                  </select>
                </div>

                {/* Rating Filter */}
                <div>
                  <label className="block text-sm font-medium text-gray-300 mb-2">Classificação</label>
                  <select
                    value={selectedRating}
                    onChange={(e) => setSelectedRating(e.target.value)}
                    className="w-full bg-white/10 border border-white/20 rounded-md px-3 py-2 text-white"
                  >
                    <option value="todos">Todas as Classificações</option>
                    {ratings.map(rating => (
                      <option key={rating} value={rating}>{rating}</option>
                    ))}
                  </select>
                </div>

                {/* Language Filter */}
                <div>
                  <label className="block text-sm font-medium text-gray-300 mb-2">Idioma</label>
                  <select
                    value={selectedLanguage}
                    onChange={(e) => setSelectedLanguage(e.target.value)}
                    className="w-full bg-white/10 border border-white/20 rounded-md px-3 py-2 text-white"
                  >
                    <option value="todos">Todos os Idiomas</option>
                    {languages.map(language => (
                      <option key={language} value={language}>{language}</option>
                    ))}
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Results Section */}
        <div className="container mx-auto px-4 py-12">
          <div className="flex justify-between items-center mb-8">
            <h2 className="text-2xl font-heading font-bold text-white">
              {filteredMovies.length} Filmes Encontrados
            </h2>
            
            <div className="flex gap-2">
              <Button variant="outline" size="sm" className="border-white/20 text-white hover:bg-white/10">
                <Star className="w-4 h-4 mr-2" />
                Por Avaliação
              </Button>
              <Button variant="outline" size="sm" className="border-white/20 text-white hover:bg-white/10">
                <Calendar className="w-4 h-4 mr-2" />
                Por Data
              </Button>
            </div>
          </div>

          {/* Movies Grid */}
          {filteredMovies.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
              {filteredMovies.map((movie) => (
                <div key={movie.id} className="animate-fade-in">
                  <MovieCard movie={movie} />
                </div>
              ))}
            </div>
          ) : (
            <div className="text-center py-16">
              <div className="glass-effect rounded-xl p-8 max-w-md mx-auto">
                <Search className="w-16 h-16 text-gray-400 mx-auto mb-4" />
                <h3 className="text-xl font-semibold text-white mb-2">Nenhum filme encontrado</h3>
                <p className="text-gray-300">Tente ajustar seus filtros ou termos de busca.</p>
              </div>
            </div>
          )}
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default Movies;
