
import { useState } from 'react';
import { Calendar, Clock, MapPin, Filter, Star } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import Header from '@/components/Header';
import Footer from '@/components/Footer';

interface SessionData {
  id: string;
  movieTitle: string;
  moviePoster: string;
  movieRating: number;
  movieGenre: string[];
  ageRating: string;
  date: string;
  time: string;
  room: string;
  roomType: '2D' | '3D';
  price: number;
  availableSeats: number;
  totalSeats: number;
}

const sampleSessions: SessionData[] = [
  {
    id: '1',
    movieTitle: 'A Origem',
    moviePoster: 'https://images.unsplash.com/photo-1489599017127-e0b6b3ec5e8d?w=300&h=400&fit=crop',
    movieRating: 4.8,
    movieGenre: ['Ficção Científica', 'Suspense'],
    ageRating: '12 anos',
    date: '2024-06-04',
    time: '14:30',
    room: 'Sala 1',
    roomType: '2D',
    price: 25.00,
    availableSeats: 45,
    totalSeats: 120
  },
  {
    id: '2',
    movieTitle: 'A Origem',
    moviePoster: 'https://images.unsplash.com/photo-1489599017127-e0b6b3ec5e8d?w=300&h=400&fit=crop',
    movieRating: 4.8,
    movieGenre: ['Ficção Científica', 'Suspense'],
    ageRating: '12 anos',
    date: '2024-06-04',
    time: '17:00',
    room: 'Sala 2',
    roomType: '3D',
    price: 35.00,
    availableSeats: 23,
    totalSeats: 100
  },
  {
    id: '3',
    movieTitle: 'Batman: O Cavaleiro das Trevas',
    moviePoster: 'https://images.unsplash.com/photo-1518676590629-3dcbd9c5a5c9?w=300&h=400&fit=crop',
    movieRating: 4.9,
    movieGenre: ['Ação', 'Crime'],
    ageRating: '12 anos',
    date: '2024-06-04',
    time: '19:30',
    room: 'Sala 3',
    roomType: '2D',
    price: 25.00,
    availableSeats: 67,
    totalSeats: 150
  },
  {
    id: '4',
    movieTitle: 'Interestelar',
    moviePoster: 'https://images.unsplash.com/photo-1446776877081-d282a0f896e2?w=300&h=400&fit=crop',
    movieRating: 4.7,
    movieGenre: ['Ficção Científica', 'Drama'],
    ageRating: '10 anos',
    date: '2024-06-04',
    time: '21:45',
    room: 'Sala 1',
    roomType: '3D',
    price: 35.00,
    availableSeats: 89,
    totalSeats: 120
  },
  {
    id: '5',
    movieTitle: 'Vingadores: Ultimato',
    moviePoster: 'https://images.unsplash.com/photo-1635805737707-575885ab0820?w=300&h=400&fit=crop',
    movieRating: 4.6,
    movieGenre: ['Ação', 'Aventura'],
    ageRating: '12 anos',
    date: '2024-06-05',
    time: '15:00',
    room: 'Sala 4',
    roomType: '3D',
    price: 35.00,
    availableSeats: 12,
    totalSeats: 180
  },
  {
    id: '6',
    movieTitle: 'Duna',
    moviePoster: 'https://images.unsplash.com/photo-1598387993441-a364f854c3e1?w=300&h=400&fit=crop',
    movieRating: 4.5,
    movieGenre: ['Ficção Científica', 'Aventura'],
    ageRating: '12 anos',
    date: '2024-06-05',
    time: '18:15',
    room: 'Sala 2',
    roomType: '2D',
    price: 25.00,
    availableSeats: 78,
    totalSeats: 100
  }
];

const Sessions = () => {
  const [selectedDate, setSelectedDate] = useState('2024-06-04');
  const [selectedRoomType, setSelectedRoomType] = useState('todos');

  const filteredSessions = sampleSessions.filter(session => {
    const matchesDate = session.date === selectedDate;
    const matchesRoomType = selectedRoomType === 'todos' || session.roomType === selectedRoomType;
    return matchesDate && matchesRoomType;
  });

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR', { 
      weekday: 'long', 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric' 
    });
  };

  const getOccupancyColor = (available: number, total: number) => {
    const percentage = (available / total) * 100;
    if (percentage > 50) return 'text-green-400';
    if (percentage > 20) return 'text-yellow-400';
    return 'text-red-400';
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
                Sessões
              </h1>
              <p className="text-xl text-gray-300 max-w-2xl mx-auto">
                Escolha o horário perfeito para sua experiência cinematográfica
              </p>
            </div>

            {/* Filters */}
            <div className="glass-effect rounded-xl p-6">
              <div className="flex items-center gap-2 mb-4">
                <Filter className="w-5 h-5 text-primary" />
                <h3 className="text-lg font-semibold text-white">Filtros</h3>
              </div>
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {/* Date Filter */}
                <div>
                  <label className="block text-sm font-medium text-gray-300 mb-2">Data</label>
                  <select
                    value={selectedDate}
                    onChange={(e) => setSelectedDate(e.target.value)}
                    className="w-full bg-white/10 border border-white/20 rounded-md px-3 py-2 text-white"
                  >
                    <option value="2024-06-04">Hoje - 04/06/2024</option>
                    <option value="2024-06-05">Amanhã - 05/06/2024</option>
                    <option value="2024-06-06">Quinta - 06/06/2024</option>
                    <option value="2024-06-07">Sexta - 07/06/2024</option>
                  </select>
                </div>

                {/* Room Type Filter */}
                <div>
                  <label className="block text-sm font-medium text-gray-300 mb-2">Tipo de Sala</label>
                  <select
                    value={selectedRoomType}
                    onChange={(e) => setSelectedRoomType(e.target.value)}
                    className="w-full bg-white/10 border border-white/20 rounded-md px-3 py-2 text-white"
                  >
                    <option value="todos">Todos os Tipos</option>
                    <option value="2D">2D</option>
                    <option value="3D">3D</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Sessions List */}
        <div className="container mx-auto px-4 py-12">
          <div className="mb-8">
            <h2 className="text-2xl font-heading font-bold text-white mb-2">
              Sessões para {formatDate(selectedDate)}
            </h2>
            <p className="text-gray-300">
              {filteredSessions.length} sessões disponíveis
            </p>
          </div>

          {filteredSessions.length > 0 ? (
            <div className="space-y-6">
              {filteredSessions.map((session) => (
                <Card key={session.id} className="bg-white/5 border-white/10 hover:bg-white/10 transition-all duration-300">
                  <CardContent className="p-6">
                    <div className="flex flex-col lg:flex-row gap-6">
                      {/* Movie Poster */}
                      <div className="flex-shrink-0">
                        <img
                          src={session.moviePoster}
                          alt={session.movieTitle}
                          className="w-24 h-36 object-cover rounded-lg"
                        />
                      </div>

                      {/* Movie Info */}
                      <div className="flex-1 space-y-4">
                        <div>
                          <div className="flex items-center gap-3 mb-2">
                            <h3 className="text-xl font-heading font-bold text-white">
                              {session.movieTitle}
                            </h3>
                            <Badge variant="secondary" className="bg-secondary text-white">
                              {session.ageRating}
                            </Badge>
                            <Badge variant="outline" className="border-primary text-primary">
                              {session.roomType}
                            </Badge>
                          </div>
                          
                          <div className="flex flex-wrap gap-2 mb-3">
                            {session.movieGenre.map((genre) => (
                              <Badge key={genre} variant="outline" className="text-xs border-white/20 text-gray-300">
                                {genre}
                              </Badge>
                            ))}
                          </div>

                          <div className="flex items-center space-x-2 text-gray-300">
                            <Star className="w-4 h-4 text-yellow-400 fill-current" />
                            <span className="text-sm font-medium">{session.movieRating}</span>
                          </div>
                        </div>

                        {/* Session Details */}
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-sm">
                          <div className="flex items-center space-x-2 text-gray-300">
                            <Clock className="w-4 h-4 text-primary" />
                            <span>{session.time}</span>
                          </div>
                          <div className="flex items-center space-x-2 text-gray-300">
                            <MapPin className="w-4 h-4 text-primary" />
                            <span>{session.room}</span>
                          </div>
                          <div className={`flex items-center space-x-2 ${getOccupancyColor(session.availableSeats, session.totalSeats)}`}>
                            <Calendar className="w-4 h-4" />
                            <span>{session.availableSeats} assentos disponíveis</span>
                          </div>
                        </div>
                      </div>

                      {/* Price and Action */}
                      <div className="flex flex-col items-end justify-between space-y-4 lg:space-y-0">
                        <div className="text-right">
                          <div className="text-2xl font-bold text-primary mb-1">
                            R$ {session.price.toFixed(2)}
                          </div>
                          <div className="text-sm text-gray-400">
                            por ingresso
                          </div>
                        </div>
                        
                        <Button 
                          className="bg-primary hover:bg-primary/90 text-white px-8"
                          disabled={session.availableSeats === 0}
                        >
                          {session.availableSeats === 0 ? 'Esgotado' : 'Comprar Ingresso'}
                        </Button>
                      </div>
                    </div>
                  </CardContent>
                </Card>
              ))}
            </div>
          ) : (
            <div className="text-center py-16">
              <div className="glass-effect rounded-xl p-8 max-w-md mx-auto">
                <Calendar className="w-16 h-16 text-gray-400 mx-auto mb-4" />
                <h3 className="text-xl font-semibold text-white mb-2">Nenhuma sessão encontrada</h3>
                <p className="text-gray-300">Tente selecionar outra data ou tipo de sala.</p>
              </div>
            </div>
          )}
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default Sessions;
