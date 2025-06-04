
import { Star, Clock, Calendar } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Movie } from '@/types';

interface MovieCardProps {
  movie: Movie;
  featured?: boolean;
}

const MovieCard = ({ movie, featured = false }: MovieCardProps) => {
  const cardClass = featured 
    ? "movie-card-hover bg-gradient-to-b from-black/40 to-black/80 backdrop-blur-sm border border-white/20 rounded-xl overflow-hidden" 
    : "movie-card-hover bg-card rounded-xl overflow-hidden border border-white/10";

  return (
    <div className={cardClass}>
      <div className="relative">
        <img
          src={movie.posterUrl || `https://images.unsplash.com/photo-1489599017127-e0b6b3ec5e8d?w=400&h=600&fit=crop`}
          alt={movie.title}
          className="w-full h-64 md:h-80 object-cover"
        />
        <div className="absolute top-4 right-4">
          <Badge variant="secondary" className="bg-secondary text-white">
            {movie.ageRating}
          </Badge>
        </div>
        <div className="absolute bottom-4 left-4 flex items-center space-x-2">
          <div className="flex items-center space-x-1 bg-black/60 rounded-full px-2 py-1">
            <Star className="w-4 h-4 text-yellow-400 fill-current" />
            <span className="text-white text-sm font-medium">{movie.rating.toFixed(1)}</span>
          </div>
        </div>
      </div>
      
      <div className="p-4 space-y-3">
        <div>
          <h3 className="text-lg font-heading font-semibold text-white mb-2 line-clamp-2">
            {movie.title}
          </h3>
          <div className="flex flex-wrap gap-2 mb-3">
            {movie.genre.slice(0, 2).map((genre) => (
              <Badge key={genre} variant="outline" className="text-xs border-white/20 text-gray-300">
                {genre}
              </Badge>
            ))}
          </div>
        </div>

        <div className="flex items-center justify-between text-sm text-gray-400">
          <div className="flex items-center space-x-1">
            <Clock className="w-4 h-4" />
            <span>{movie.duration} min</span>
          </div>
          <div className="flex items-center space-x-1">
            <Calendar className="w-4 h-4" />
            <span>{new Date(movie.releaseDate).getFullYear()}</span>
          </div>
        </div>

        <p className="text-gray-300 text-sm line-clamp-3 leading-relaxed">
          {movie.synopsis}
        </p>

        <div className="flex space-x-2 pt-2">
          <Button className="flex-1 bg-primary hover:bg-primary/90 text-white">
            Comprar Agora
          </Button>
          <Button variant="outline" className="border-white/20 text-white hover:bg-white/10">
            Detalhes
          </Button>
        </div>
      </div>
    </div>
  );
};

export default MovieCard;
