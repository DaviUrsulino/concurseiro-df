import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

interface TopicoDisciplina {
  disciplina: string;
  topicos: string[];
}

export const generateStudyGuidePDF = (
  concursoTitulo: string,
  orgao: string,
  cargoNome: string,
  conteudoProgramaticoJson: string
) => {
  try {
    const disciplinas: TopicoDisciplina[] = JSON.parse(conteudoProgramaticoJson);
    
    // Create new PDF document
    const doc = new jsPDF();
    
    // Add header
    doc.setFontSize(22);
    doc.setTextColor(41, 128, 185); // Primary blue color
    doc.text('Guia de Estudos Estruturado', 14, 22);
    
    doc.setFontSize(12);
    doc.setTextColor(44, 62, 80);
    doc.text(`Concurso: ${concursoTitulo}`, 14, 32);
    doc.text(`Órgão: ${orgao}`, 14, 39);
    doc.text(`Cargo: ${cargoNome}`, 14, 46);
    
    doc.setDrawColor(200, 200, 200);
    doc.line(14, 52, 196, 52);

    let startY = 60;

    // Add tables for each discipline
    disciplinas.forEach((disc) => {
      autoTable(doc, {
        startY: startY,
        head: [['[ ]', disc.disciplina]],
        body: disc.topicos.map(t => ['[ ]', t]),
        theme: 'grid',
        headStyles: {
          fillColor: [41, 128, 185],
          textColor: 255,
          fontStyle: 'bold',
        },
        columnStyles: {
          0: { cellWidth: 15, halign: 'center', fontStyle: 'bold' },
          1: { cellWidth: 'auto' }
        },
        styles: {
          fontSize: 10,
          cellPadding: 4,
        },
        margin: { left: 14, right: 14 }
      });
      
      // Update Y position for next table
      startY = (doc as any).lastAutoTable.finalY + 10;
      
      // Add new page if too close to bottom
      if (startY > 270) {
        doc.addPage();
        startY = 20;
      }
    });
    
    // Add footer
    const pageCount = (doc.internal as any).getNumberOfPages();
    for (let i = 1; i <= pageCount; i++) {
      doc.setPage(i);
      doc.setFontSize(9);
      doc.setTextColor(150, 150, 150);
      doc.text(
        `Gerado por Concurseiro DF (Agregador Inteligente) - Página ${i} de ${pageCount}`,
        14,
        290
      );
    }
    
    // Download PDF
    doc.save(`Guia_Estudos_${cargoNome.replace(/\s+/g, '_')}.pdf`);
    
  } catch (err) {
    console.error("Erro ao gerar PDF do guia de estudos", err);
    alert("Não foi possível gerar o guia de estudos. Formato de conteúdo inválido.");
  }
};
