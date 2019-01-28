
package estacionamento;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ManipuladorDeArquivos {
       
        
        //Carrega para leitura o Arquivo VAGAS.txt, carrega todas as vagas em um ArrayList(relatorio).
	public static Relatorio lerVagas() throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader("VAGAS.txt"));
		ArrayList<String> listaDeVagas = new ArrayList();
                Relatorio relatorio = new Relatorio();
                relatorio.getInstance();
                
                if(relatorio.getListaVagas().size()>0)
                    relatorio.getListaVagas().clear();
                
                String linha;

		do {
			linha = buffRead.readLine();
			if (linha != null)
				listaDeVagas.add(linha);
		} while(linha != null);
		
                for(String Y : listaDeVagas)
                {
                    String[] info = Y.split(",");
                    Vaga vaga = new Vaga(Integer.valueOf(info[0]),Float.valueOf(info[1]),Float.valueOf(info[2]),Float.valueOf(info[3]),Float.valueOf(info[4]));

                    relatorio.addVaga(vaga);
                }
                
		buffRead.close();
		return relatorio;
	}
        
        
        //Carrega para leitura o Arquivo VEICULOS.txt, carrega todas as vagas em um ArrayList(Veiculos).
        public static ArrayList<Veiculo> lerVeiculos() throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader("VEICULOS.txt"));
		ArrayList<String> listaDeVeiculos = new ArrayList<String>();
		ArrayList<Veiculo> Veiculos = new ArrayList();
                String linha;

		do {
			linha = buffRead.readLine();
			if (linha != null)
				listaDeVeiculos.add(linha);
		} while(linha != null);
		
                for(String X : listaDeVeiculos)
                {
                    String[] info = X.split(",");
                    Veiculo veiculo = new Veiculo(info[0],Integer.valueOf(info[1]),Float.valueOf(info[2]),Float.valueOf(info[3]),Float.valueOf(info[4]),Float.valueOf(info[5]));

                    Veiculos.add(veiculo);  
                }  
                
		buffRead.close();
		return Veiculos;
	}
        
        //Cria uma pasta com o horário de inicio da execução e cria um arquivo .txt do log da execução, ja armazenando nele o inicio da mesma.
	public static String escreverInicioLog() throws IOException {
		Calendar hoje = Calendar.getInstance();
                String data = (hoje.getTime()).toString();
                data = ManipuladorDeArquivos.converteIngPortData(data);
                
                String[] dataTxt = data.split(" ");
                
                dataTxt[3] = dataTxt[3].replace(":", "'");
                
                File dir = new File(dataTxt[2]+" de "+dataTxt[1]+" de "+dataTxt[5]+" -- "+dataTxt[0]+" "+dataTxt[3]);        
                dir.mkdir();
                String pasta = dir.getPath();
                BufferedWriter BuffW = new BufferedWriter(new FileWriter(pasta+"/log.txt",true));
                
                data = padronizaData(data);
                
                BuffW.write("Início de execução: ");
                BuffW.write(data);
                BuffW.newLine();
                BuffW.close();  
                
                return dataTxt[2]+" de "+dataTxt[1]+" de "+dataTxt[5]+" -- "+dataTxt[0]+" "+dataTxt[3];
	}
        
        //Escreve no arquivo de log o horário do fim da execução.
        public static void escreverFimLog(String nomeArquivo) throws IOException {
		Calendar hoje = Calendar.getInstance();
                String data = (hoje.getTime()).toString();
                data = ManipuladorDeArquivos.converteIngPortData(data);
                String[] dataTxt = data.split(" ");
                
                BufferedWriter BuffW = new BufferedWriter(new FileWriter(nomeArquivo+"/log.txt",true));
                
                data = padronizaData(data);
                
                BuffW.write("Fim de execução: ");
                BuffW.write(data);
                BuffW.newLine();
                BuffW.close();                   
	}
        
        //Escreve no arquivo de log alguem entrada ou saida realizada com sucesso ou não, com seu respectivo horário.
        public void registraLog(String entradaSaida,String sucessoFalha,String chassi,String IdVaga,String nomeArquivo) throws IOException {
		Calendar hoje = Calendar.getInstance();
                String data = (hoje.getTime()).toString();
                data = ManipuladorDeArquivos.converteIngPortData(data);
                String[] dataTxt = data.split(" ");
                
                BufferedWriter BuffW = new BufferedWriter(new FileWriter(nomeArquivo+"/log.txt",true));
                
                data = padronizaData(data);
                
                BuffW.write(entradaSaida+","+sucessoFalha+","+chassi+","+IdVaga+","+data);
                BuffW.newLine();
                BuffW.close();                   
	}
        
        //Escreveno arquivo de log o horário de reinício, após carregar execução salva.
        public static String escreverReinicioLog(String nomeArquivo) throws IOException {
		Calendar hoje = Calendar.getInstance();
                String data = (hoje.getTime()).toString();
                data = ManipuladorDeArquivos.converteIngPortData(data);
                String[] dataTxt = data.split(" ");
                
                dataTxt[3] = dataTxt[3].replace(":", "'");
                
                BufferedWriter BuffW = new BufferedWriter(new FileWriter(nomeArquivo+"/log.txt",true));
                
                data = padronizaData(data);
                
                BuffW.write("Reinício da execução: ");
                BuffW.write(data);
                BuffW.newLine();
                BuffW.close();
                
                return dataTxt[2]+" de "+dataTxt[1]+" de "+dataTxt[5]+" -- "+dataTxt[0]+" "+dataTxt[3];
	}
        
        //Cria um arquivo .dat armazenando todos os objetos de interesse para a continuação da execução no futuro. (Lista de carros,Relatorio das vagas,Data de inicio).
        //O nome do arquivo .dat fica com o horário que a execução foi salva.
        public void salvarExecucao(ArrayList<Veiculo> filaCarros,String horaInicio) throws FileNotFoundException, IOException{
        Calendar hoje = Calendar.getInstance();
        String data = (hoje.getTime()).toString();
        data = ManipuladorDeArquivos.converteIngPortData(data);
        Relatorio relatorio = new Relatorio();
        relatorio.getInstance();
        
        String[] dataTxt = data.split(" ");      
        dataTxt[3] = dataTxt[3].replace(":", "'");        

        horaInicio = horaInicio.replace(":", "'");
        horaInicio = horaInicio.replace("/", " de ");
        
        BufferedWriter BuffW = new BufferedWriter(new FileWriter(horaInicio+"/log.txt",true));
        
        data = padronizaData(data);
        
        BuffW.write("Execução salva: ");
        BuffW.write(data);
        BuffW.newLine();
        BuffW.close();
        
        ObjectOutputStream escreve = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(horaInicio+"/Execução Salva - "+dataTxt[2]+" de "+dataTxt[1]+" de "+dataTxt[5]+" -- "+dataTxt[0]+" "+dataTxt[3]+".dat")));
        
        int tamanhoCarros = filaCarros.size();
        int tamanhoVagas = relatorio.getListaVagas().size();
        
        escreve.writeInt(tamanhoCarros);
        escreve.writeInt(tamanhoVagas);
        
        for(Veiculo X: filaCarros)
                 escreve.writeObject(X);
            for(Vaga Y: relatorio.getListaVagas()){
                 escreve.writeObject(Y);
            }
       
       escreve.writeChars(horaInicio);
       escreve.close();
   }
        
        //Faz o usuário escolher um arquivo salvo anteriormente, recupera todos os dados de interesse e recomeça a execução.
        public String carregaExecucao(Visual visual) throws IOException, ClassNotFoundException{
       final JFileChooser fc = new JFileChooser();
       FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos .dat (*.dat)", "dat");    
       ArrayList<Veiculo> listaCarros = new ArrayList();
       ArrayList<Vaga> listaVagas = new ArrayList();
       Relatorio vagas = new Relatorio();
       vagas.getInstance();
       
       File file = new File("VAGAS.txt");

       fc.setDialogTitle("Carregar execução");
       fc.setFileFilter(filtro);
       fc.setCurrentDirectory(file);
       File arq;
       int escolha = fc.showOpenDialog(visual);
       String path="";
       if(escolha == 0){
           arq = fc.getSelectedFile();
           path = arq.getAbsolutePath();
       
            FileInputStream input = new FileInputStream(path);
            ObjectInputStream le = new ObjectInputStream(input);
            Veiculo veiculo;
            Vaga vaga;
            int tamanhoCarros = le.readInt();
            int tamanhoVagas = le.readInt();

            for(int x=0;x<tamanhoCarros;x++){
                Object carro = le.readObject();
                veiculo = (Veiculo)carro;
                listaCarros.add(veiculo);
            }
            for(int y=0;y<tamanhoVagas;y++){
                Object va = le.readObject();
                vaga = (Vaga)va;
                listaVagas.add(vaga);
            }
            String dataInicio= "";

            while(le.available()>0)
            dataInicio = dataInicio + le.readChar();

            ManipulaTabelas manip = new ManipulaTabelas(visual,null);

            manip.preencheCarros(listaCarros);
            vagas.setListaVagas(listaVagas);
            manip.preencheVagas();

           return dataInicio;
        }else{
           return "";
       }
      
   }
        
        //Converte a data recebida do sistema para portugues. Ex: Mon -> Seg , Sep -> Setembro.
        public static String converteIngPortData(String data){
        String[] dataHoraString = data.split(" ");
        String[] dataHoraString2 = data.split(" ",2);
        String dataFinal=null;
        
        String[] mes = dataHoraString2[1].split(" ");
        String[] mes2= dataHoraString2[1].split(" ",2);
        String mesFinal=null;
        
        
        switch (mes2[0]) {
            case "Jan":
                mesFinal = "Janeiro "+mes2[1];
                break;
            case "Feb":
                mesFinal = "Fevereiro "+mes2[1];
                break;
            case "Mar":
                mesFinal = "Março "+mes2[1];
                break;
            case "Apr":
                mesFinal = "Abril "+mes2[1];
                break;
            case "May":
                mesFinal = "Maio "+mes2[1];
                break;
            case "Jun":
                mesFinal = "Junho "+mes2[1];
                break;
            case "Jul":
                mesFinal = "Julho "+mes2[1];
                break;
            case "Aug":
                mesFinal = "Agosto "+mes2[1];
                break;
            case "Sep":
                mesFinal = "Setembro "+mes2[1];
                break;
            case "Oct":
                mesFinal = "Outubro "+mes2[1];
                break;
            case "Nov":
                mesFinal = "Novembro "+mes2[1];
                break;
            case "Dec":
                mesFinal = "Dezembro "+mes2[1];
                break;
            default:
                break;
        }
        
        
        
        switch (dataHoraString[0]) {
            case "Mon":
                dataFinal = "Segunda "+mesFinal;
                break;
            case "Tue":
                dataFinal = "Terça "+mesFinal;
                break;
            case "Wed":
                dataFinal = "Quarta "+mesFinal;
                break;
            case "Thu":
                dataFinal = "Quinta "+mesFinal;
                break;
            case "Fri":
                dataFinal = "Sexta "+mesFinal;
                break;
            case "Sat":
                dataFinal = "Sábado "+mesFinal;
                break;
            case "Sun":
                dataFinal = " Domingo "+mesFinal;
                break;
            default:
                break;
        }

       return dataFinal; 
    }
        
        //Muda formato da data recebida do sistema.
        public static String padronizaData(String data){
            String dataPadrao="";
            String[] dataTxt = data.split(" ");
            
            dataPadrao = dataTxt[2]+" de "+dataTxt[1]+" de "+dataTxt[5]+" -- "+dataTxt[0]+" "+dataTxt[3];
            
            return dataPadrao;
        }
}