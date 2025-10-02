import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Semantico implements Constants {

    private final Map<String, Integer> variaveis = new HashMap<>();
    private final Stack<Integer> pilha = new Stack<>();
    private String variavelAtualString = null;

    public void executeAction(int action, Token token) throws SemanticError {

        switch (action) {

            case 15: { // GUARDAR NOME DA VARIAVEL (para atribuição posterior)
                variavelAtualString = token.getLexeme();
                break;
            }

            // COMANDOS
            
            case 1: { // EFETUAR A ATRIBUICAO
                if (variavelAtualString == null)
                    throw new SemanticError("Variável à esquerda não definida na atribuição.");
                int valor = pilha.pop();
                variaveis.put(variavelAtualString, valor);
                variavelAtualString = null;
                break;
            }

            case 2: { // SHOW
                int v = pilha.pop();
                System.out.println("-= SHOW =-");
                System.out.println("DECIMAL: " + v);
                System.out.println("BINÁRIO: " + Integer.toBinaryString(v));
                break;
            }

            // OPERACOES BINARIAS
            case 3: { // SOMA
                int b = pilha.pop();
                int a = pilha.pop();
                pilha.push(a + b);
                break;
            }
            case 4: { // SUBTRACAO
                int b = pilha.pop();
                int a = pilha.pop();
                pilha.push(a - b);
                break;
            }
            case 5: { // MULTIPLICACAO
                int b = pilha.pop();
                int a = pilha.pop();
                pilha.push(a * b);
                break;
            }
            case 6: { // DIVISAO
                int b = pilha.pop();
                int a = pilha.pop();
                pilha.push(a / b);
                break;
            }
            case 7: { // EXPONENCIACAO
                int b = pilha.pop();
                int a = pilha.pop();
                pilha.push((int) Math.pow(a, b));
                break;
            }

            // UNARIOS
            case 8: { // UNARIO POSITIVO (nada a ser feito)
                break;
            }
            case 9: { // UNARIO NEGATIVO
                int x = pilha.pop();
                pilha.push(-x);
                break;
            }
            case 10: { // SEM UNARIO EXPLICITO (nao faz nada)
                break;
            }

            // OPERANDOS
            case 11: { // CONSTANTE BINARIA
                int v = Integer.parseInt(token.getLexeme(), 2);
                pilha.push(v);
                break;
            }
            case 12: { // VARIAVEL
                String nome = token.getLexeme();
                int v = variaveis.getOrDefault(nome, 0);
                pilha.push(v);
                break;
            }
            case 13: { // ABRE/FECHA PARENTESES
                break;
            }
            case 14: { // LOG2
                int x = pilha.pop();
                if (x <= 0) throw new SemanticError("log definido apenas para x > 0.");
                int v = (int) Math.floor(Math.log(x)/Math.log(2)); // Math.log(x)
                pilha.push(v);
                break;
            }

            default:
                throw new SemanticError("Ação semântica não tratada: #" + action);
        }
    }
}
