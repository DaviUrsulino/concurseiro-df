-- Atualiza todos os links locais antigos ou nulos para um arquivo PDF real e testável
UPDATE tb_andamento
SET link_documento = 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf'
WHERE link_documento = 'edital.pdf' OR link_documento = 'dummy.pdf' OR link_documento IS NULL;
