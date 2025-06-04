
export interface Movie {
  id: string;
  title: string;
  genre: string[];
  ageRating: string;
  language: string;
  duration: number;
  rating: number;
  synopsis: string;
  posterUrl: string;
  trailerUrl?: string;
  cast: string[];
  director: string;
  releaseDate: string;
  featured?: boolean;
}

export interface Session {
  id: string;
  movieId: string;
  roomId: string;
  datetime: string;
  price: number;
  availableSeats: number;
  totalSeats: number;
  format: '2D' | '3D';
}

export interface Room {
  id: string;
  name: string;
  type: '2D' | '3D';
  seats: Seat[][];
  capacity: number;
}

export interface Seat {
  id: string;
  row: string;
  number: number;
  type: 'common' | 'vip' | 'pcd';
  isOccupied: boolean;
  isSelected?: boolean;
}

export interface User {
  id: string;
  name: string;
  email: string;
  loyaltyPoints: number;
  preferences: {
    genres: string[];
    languages: string[];
  };
}

export interface Review {
  id: string;
  movieId: string;
  userId: string;
  userName: string;
  rating: number;
  comment: string;
  date: string;
  verified: boolean;
}

export interface CartItem {
  sessionId: string;
  movie: Movie;
  session: Session;
  seats: Seat[];
  subtotal: number;
}

export interface Promotion {
  id: string;
  title: string;
  description: string;
  discountPercentage: number;
  validUntil: string;
  imageUrl: string;
}
