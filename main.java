import java.util.ArrayList;
import java.util.Scanner;
public final class Main {
    private static ArrayList<Reserva> reservas = new ArrayList<Reserva>();
    private static ArrayList<Reserva> listaEspera = new ArrayList<Reserva>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("(Digite 0 Para Ver Opcoes)");
        while(!exit){
            try {
                System.out.print("User> ");
                String input = scanner.nextLine().trim().toLowerCase();
        
                if ("".equals(input)) {
                } else if ("0".equals(input)) {
                    help();
                } else if ("1".equals(input)) {
                    reservar_mesa();
                } else if ("2".equals(input)) {
                    verificarExistencia(pesquisar_reserva(informarCodigo()));
                } else if ("3".equals(input)) {
                    imprimir_reservas();
                } else if ("4".equals(input)) {
                    imprimir_espera();
                } else if ("5".equals(input)) {
                    cancelar_reserva(informarCodigo());
                } else if ("6".equals(input)) {
                    exit = true;
                }else {
                    System.err.println("Opcao Invalida. Digite \"0\" para ajuda");
                    throw new UnsupportedOperationException();
                }
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            }
        }
    }

    private static void help() {    
        System.out.println(" Restaurante Sabor Sofisticado");
        System.out.println("0. Menu de Opcoes");
        System.out.println("1. Reservar de mesa");
        System.out.println("2. Pesquisa de reserva");
        System.out.println("3. Impressao de reserva");
        System.out.println("4. Lista de espera");
        System.out.println("5. Cancelar Reserva");
        System.out.println("6. Terminar");
    }
    private static void reservar_mesa(){
        Scanner scanner = new Scanner(System.in);
        Cliente novo_cliente = null;

        TipoPessoa tp_c = escolherCliente();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        switch (tp_c) {
            case FISICA:
                System.out.print("Digite o CPF: ");
                String cpf = scanner.nextLine();
                if (!codigoJaExiste(cpf)){
                    PessoaFisica pf = new PessoaFisica(nome, cpf);
                    novo_cliente = pf;
                } else {
                    System.out.println("CPF Ja Inserido");
                }
                break;
            case JURIDICA:
                System.out.print("Insira o CNPJ ");
                String cnpj = scanner.nextLine();
                if (!codigoJaExiste(cnpj)) {
                    PessoaJuridica pj = new PessoaJuridica(nome, cnpj);
                    novo_cliente = pj;
                }
                else {
                    System.out.println("CNPJ ja Cadastrado!");
                }
                break;
        }

        TipoPagamento tp_p = escolherPagamento();
        boolean pagamentoAVista;
        switch (tp_p) {
            case AVISTA:
                pagamentoAVista = true;
                break;
            case PARCELADO:
                pagamentoAVista = false;
                break;
            default:
                pagamentoAVista = false;
        }

        Reserva nova_reserva = new Reserva(novo_cliente, pagamentoAVista);
        if( reservas.size() < 7) {
            reservas.add(nova_reserva);
        } else {
            listaEspera.add(nova_reserva);
        }

    }

    private static Reserva pesquisar_reserva(String dado){

        Reserva reservaEncontrada = null;
        for (Reserva reserva : reservas) {
            if (reserva.cliente instanceof PessoaFisica) {
                PessoaFisica cliente = (PessoaFisica) reserva.cliente;
                if (cliente.getCpf().equals(dado)){
                    reservaEncontrada = reserva;
                }
            } else {
                PessoaJuridica cliente = (PessoaJuridica) reserva.cliente;
                if (cliente.getCnpj().equals(dado)){
                    reservaEncontrada = reserva;
                }
            }
        }

        

        return reservaEncontrada;
    }

    private static boolean codigoJaExiste(String codigo){
        if (pesquisar_reserva(codigo)==null) {
            return false;
        }
        return true;
    }

    private static void imprimir_reservas(){
        for (Reserva reserva : reservas) {
            System.out.println(reserva);
        }
    }

    private static void imprimir_espera(){
        for (Reserva reserva : listaEspera) {
            System.out.println(reserva);
        }
    }   

    private static void cancelar_reserva(String codigo){
        Reserva reserva = pesquisar_reserva(codigo);
        reservas.remove(reserva);
        System.out.println(">> Reserva Cancelada <<");
    }

    private static TipoPessoa escolherCliente(){
        Scanner scanner = new Scanner(System.in);
        String tp = "";
        while (!tp.equals("j") && !tp.equals("f")) {
            System.out.print("Tipo do Cliente? [F|J] ");
            tp = scanner.nextLine().toLowerCase();    
            if (!tp.equals("j") && !tp.equals("f")) {
                System.err.println("F: Física | J: Jurídica");
            }
        }
        return tp.equals("f") ? TipoPessoa.FISICA : TipoPessoa.JURIDICA;
    }

    private static TipoPagamento escolherPagamento(){
        Scanner scanner = new Scanner(System.in);
        String tp = "";
        while (!tp.equals("a") && !tp.equals("p")) {
            System.out.print("Tipo do Pagamento? [A|P] ");
            tp = scanner.nextLine().toLowerCase();    
            if (!tp.equals("a") && !tp.equals("p")) {
                System.err.println("A: A Vista | P: Parcelado");
            }
        }
        return tp.equals("a") ? TipoPagamento.AVISTA : TipoPagamento.PARCELADO;
    }

    public static String informarCodigo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o codigo: ");
        String codigo = scanner.nextLine();
        return codigo;
    }
    public static void verificarExistencia(Reserva reservaEncontrada) {
        if (reservaEncontrada==(null)){
            System.out.println(" Cliente Não Possui Reserva! ");
        } else {
            System.out.println(" Cliente Possui Reserva ");
        }
    }
}