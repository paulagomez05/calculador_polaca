package calculadorarpn;

/**
 *
 * @author Jorge Daniel Gomez Vanegas
 */
import java.util.*;
public class CalculadoraRPN {

    /**
     * @param args the command line arguments
     */
    private static Stack<Double> operationStack = new Stack<Double>();
    private static ArrayList<String> variableNames = new ArrayList<String>();
    private static ArrayList<Double> variableValues = new ArrayList<Double>();
    private static Scanner lector= new Scanner(System.in);
    private static double[] pila;
    public static void main(String[] args) {
        System.out.println("Calculadora en polaco inverso");
        System.out.println("Ingrese números y operaciones >>");
        
        
        while(lector.hasNextLine()) {
            String s = lector.nextLine();
            evaluar(s);
            System.out.print("Ingrese números y operaciones >> \n");
        }
        
    }
    public static void evaluar(String s) {

        if(s.equals("salir")) {
            System.exit(0);
        }else if(s.equals("buscar")){
            System.out.println("ingrese el numero a buscar >>");
            String buscado=lector.nextLine();
            buscar(buscado);
        } else {
            calcular(s);
        }
    }
    public static void calcular(String s){
        ArrayList<String> input = new ArrayList<String>();
        Collections.addAll(input, s.trim().split(" ")); // agrega todos los elementos separados por espacios
        input.removeAll(Arrays.asList(null, ""));
        
        if(input.size() == 1) {                         // si es una entrada de un numero
            double d = getValue(s);
            if(!Double.isNaN(d)) System.out.println("\t" + d);// si no es NaN imprime el numero en double            
            return;
        }
        int startIndex = 0;
        for(int i = startIndex; i < input.size(); i++) {
            String n = input.get(i);
            if(isOperator(n)) {                         //evalua si hay operadores
                if(operationStack.size() > 1) {
                    /*pila=new double[operationStack.size()];
                    for(i=0;i<operationStack.size();i++){
                        pila[i]=operationStack.get(i);
                    }   */                 
                    operationStack.push(doOperation(n));
                } else {
                    System.out.println("\tOperación inválida!");
                    return;
                }
            } else {
                double d = getValue(n);
                if(!Double.isNaN(d)) {                  //si es un numero lo ingresa al stack
                    operationStack.push(d);
                } else {
                    operationStack.clear();
                    return;
                }
            }
        }
        double result = operationStack.pop();
        if(operationStack.size() > 0) {
            System.out.println("\tOperación inválida!");
            operationStack.clear();
            return;
        }
        System.out.println("\t RESULTADO: " + result);
        
    }/*boolean hasVariableAssignment = input.contains("=");
        String var = "";
        int startIndex = 0;
        if(hasVariableAssignment) {
            var = input.get(0);
            startIndex = 2;
        } else {
            startIndex = 0;
        }*/
        

    public static double doOperation(String s) {
        char op = s.charAt(0);
        double a = operationStack.pop();
        double b = operationStack.pop();
        switch (op) {
            case '+':
                return b + a;
            case '-':
                return b - a;
            case '*':
                return b * a;
            case '/':
                return b / a;
            default:
                return Double.NaN;
        }
    }

    public static double getValue(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            try {
                return variableValues.get(variableNames.indexOf(s));
            } catch (Exception e) {
                System.out.printf("\t%s no encontrado.\n", s);
                return Double.NaN;
            }
        }
    }
    public static void buscar(String buscado){
        Double z=Double.parseDouble(buscado);
        int encontrados=0;
        for(int k=0;k<pila.length;k++){
            if (pila[k]==z){
                encontrados++;
                System.out.println("El numero buscado está en la pila en la posicion "+k);
            }
        }
        if(encontrados==0){
            System.out.println("su numero no está en la pila");
        }
    }

  /*  public static void replaceAddValue(String var, double value) {

        if(variableNames.contains(var)) {
            int index = variableNames.indexOf(var);
            variableValues.set(index, value);
        } else {
            variableNames.add(var);
            variableValues.add(value);
        }
    }*/

    public static boolean isOperator(String s) {
        char c = s.charAt(0);
        return c == '+' || c == '-' || c == '/' || c == '*';
    }
    
}
