
import Header from '@/components/Header';
import HeroSection from '@/components/HeroSection';
import MoviesSection from '@/components/MoviesSection';
import Footer from '@/components/Footer';

const Index = () => {
  return (
    <div className="min-h-screen">
      <Header />
      <main>
        <HeroSection />
        <MoviesSection />
      </main>
      <Footer />
    </div>
  );
};

export default Index;
