import * as React from "react";

export interface BadgeProps extends React.HTMLAttributes<HTMLDivElement> {
  variant?: "aberto" | "andamento" | "finalizado" | "default";
}

export function Badge({ className = "", variant = "default", ...props }: BadgeProps) {
  const baseStyles = "inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-bold transition-colors focus-ring";
  
  const variants = {
    aberto: "bg-green-100 text-green-800 dark:bg-green-900/50 dark:text-green-300",
    andamento: "bg-blue-100 text-blue-800 dark:bg-blue-900/50 dark:text-blue-300",
    finalizado: "bg-gray-100 text-gray-800 dark:bg-gray-800 dark:text-gray-300",
    default: "bg-secondary text-secondary-foreground",
  };

  return (
    <div className={`${baseStyles} ${variants[variant]} ${className}`} {...props} />
  );
}
