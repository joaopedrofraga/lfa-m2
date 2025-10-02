import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws AnalysisError {
        try {
            FileReader arquivo = new FileReader("input.txt");
            StringBuilder conteudo = new StringBuilder();

            try (BufferedReader leitor = new BufferedReader(arquivo)) {
                String linha;
                while ((linha = leitor.readLine()) != null) {
                    conteudo.append(linha).append("\n");
                }
            }

            Lexico lexico = new Lexico(conteudo.toString());

            Semantico semantico = new Semantico();

            Sintatico sintatico = new Sintatico();

            sintatico.parse(lexico, semantico);

            System.out.println("\nFIM!!!\n");
        } catch (LexicalError e) {
            System.err.println("Erro léxico: " + e.getMessage());
        } catch (SemanticError e) {
            System.err.println("Erro semântico: " + e.getMessage());
        } catch (SyntacticError e) {
            System.err.println("Erro sintático: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        }
    }
}
