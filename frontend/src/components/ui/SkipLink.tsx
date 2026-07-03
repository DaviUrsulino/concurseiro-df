export default function SkipLink() {
  return (
    <a
      href="#main-content"
      className="sr-only focus:not-sr-only focus:absolute focus:z-50 focus:top-4 focus:left-4 focus:px-4 focus:py-2 focus:bg-primary focus:text-primary-foreground focus:font-bold focus:rounded-md focus:ring-4 focus:ring-ring focus:outline-none transition-all shadow-lg"
    >
      Pular para o conteúdo principal
    </a>
  );
}
