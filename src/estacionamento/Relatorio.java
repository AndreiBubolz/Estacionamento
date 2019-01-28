/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estacionamento;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Relatorio {
    private static Relatorio relatorio = new Relatorio();
    private static ArrayList<Vaga> listaVagas = new ArrayList();

    
    public ArrayList<Vaga> getListaVagas() {
        return listaVagas;
    } 
    
    public void setListaVagas(ArrayList<Vaga> lista){
        listaVagas = lista;
    }
    
    public void addVaga(Vaga vaga){
        listaVagas.add(vaga);
    }
    
    public Relatorio getInstance(){
    if(relatorio == null) 
        relatorio = new Relatorio();
    
    return relatorio;
    }
    
    //Retorno, se presente, o veiculo estacionado atualmente nada vaga .
   public Veiculo getCarroEstacionado(int ID){
       Veiculo veiculo=null;
       for(Vaga X: listaVagas){
           if(X.getID() == ID)
           {
               int tamanho = X.veiculosEstacionados.size();
               veiculo = X.veiculosEstacionados.get(tamanho-1);
           }
       } 
       return veiculo;
   }
   
   //Armazena em .txt o relatorio completo da execução.(Sobrescreve caso já tenha um relatório salvo).
   public void escreveRelatorio(String diretorio) throws IOException{
        
        File arquivo = new File(diretorio+"/relatorio.txt");
        arquivo.delete();
        
        BufferedWriter esc = new BufferedWriter(new FileWriter(diretorio+"/relatorio.txt",true));
        
        esc.write("Relatório de Execução de "+diretorio+".");
        esc.newLine();
        for(Vaga X: listaVagas){
            esc.write("Vaga ID "+X.getID()+":");
            esc.newLine();
            esc.write("Veículos estacionados - "+X.getVezesUsada());
            esc.newLine();
            esc.write("Falhas ao estacionar nessa vaga - "+X.getFalhasUso());
            esc.newLine();
        }
        esc.newLine();
        
        esc.write("Total de veículos estacionados: "+retornaEstatisticasCarros(0));
        esc.newLine();
        esc.write("Veiculos curtos: "+retornaEstatisticasCarros(1));
        esc.newLine();
        esc.write("Veiculos longos: "+(retornaEstatisticasCarros(0) - retornaEstatisticasCarros(1)));
        esc.newLine();
        esc.write("Veiculos leves: "+retornaEstatisticasCarros(2));
        esc.newLine();
        esc.write("Veiculos pesados: "+(retornaEstatisticasCarros(0) - retornaEstatisticasCarros(2)));
        esc.newLine();
        esc.write("Veiculos baixos: "+retornaEstatisticasCarros(3));
        esc.newLine();
        esc.write("Veiculos altos: "+(retornaEstatisticasCarros(0) - retornaEstatisticasCarros(3)));
        esc.newLine();
        esc.write("Veiculos estreitos: "+retornaEstatisticasCarros(4));
        esc.newLine();
        esc.write("Veiculos largos: "+(retornaEstatisticasCarros(0) - retornaEstatisticasCarros(4)));
        esc.newLine();
        
        ArrayList<Veiculo> ordenada = ordenaVeiculos();
        
        esc.newLine();
        esc.write("Lista de veículos estacionados:");
        esc.newLine();
        for(Veiculo X: ordenada){
            esc.write("Modelo: "+X.getModelo()+"  "+"Chassi: "+"  "+X.getChassi()+"  "+"Peso: "+X.getPeso()+"  "+"Altura: "+X.getAltura()+"  "+"Comprimento: "+X.getComprimento()+"  "+"Largura: "+X.getLargura());
            esc.newLine();
        }
        
        esc.close();
        
   }
   
   //Retorna as estatisticas de todos carros estacionados no dia.
   public int retornaEstatisticasCarros(int codigoOp){
       //Se codigoOp = 0 retorna quantidade de carros estacionados hoje;
       //Se codigoOp = 1 retorna quantidade de carros longos/curtos;
       //Se codigoOp = 2 retorna quantidade de carros leves/pesados;
       //Se codigoOp = 3 retorna quantidade de carros baixos/altos;
       //Se codigoOp = 4 retorna quantidade de carros estreitos/largos;
       int estacionados=0,curtos=0,leves=0,baixos=0,estreitos=0;
       
       
       for(Vaga vaga:listaVagas){
           if(vaga.veiculosEstacionados != null){
                for(Veiculo veiculo: vaga.veiculosEstacionados){
                    estacionados++;
                    if(veiculo.getPeso() < 2500)
                       leves++;
                    if(veiculo.getComprimento() < 2.5)
                       curtos++;
                    if(veiculo.getLargura() < 1.6)
                        estreitos++;
                    if(veiculo.getAltura() < 1.7)
                        baixos++;
                }
           }
       }
       
       switch(codigoOp){
               case 0:
                  return estacionados;
               case 1:
                  return curtos;
               case 2:
                  return leves;
               case 3:
                  return baixos;
               case 4:
                  return estreitos;
               default:
                  return -1;
        }
       
   }
   
   //Ordena os carros estacionados no dia em ordem, de acordo com a especificação do trabalho.
   public ArrayList<Veiculo> ordenaVeiculos(){
      ArrayList<Veiculo> lista = new ArrayList();
      
      for(Vaga vaga:listaVagas){
           if(vaga.veiculosEstacionados != null){
                for(Veiculo veiculo: vaga.veiculosEstacionados){
                    lista.add(veiculo);
                }
           }
       }
      
         int posicao=0;
         Veiculo aux;
         for(int x=0;x<lista.size();x++)
         {
             posicao=x;
             aux = lista.get(x);
             for(int y=(x+1);y<lista.size();y++){
                    if(lista.get(y).getPeso() > aux.getPeso()){
                        aux = lista.get(y);
                        posicao = y;
                       }
                    else if(lista.get(y).getPeso() == aux.getPeso()){
                         if(lista.get(y).getAltura() > aux.getAltura()){
                             aux = lista.get(y);
                             posicao = y;
                             }
                         else if(lista.get(y).getAltura() == aux.getAltura()){
                              if(lista.get(y).getComprimento() > aux.getComprimento()){
                                  aux = lista.get(y);
                                  posicao = y;
                                  }
                              else if(lista.get(y).getComprimento() == aux.getComprimento())
                              {
                                  if(lista.get(y).getLargura() > aux.getLargura()){
                                      aux = lista.get(y);
                                      posicao = y;
                                       }
                                  else if(lista.get(y).getLargura() == aux.getLargura()){
                                      aux = lista.get(y);
                                      posicao = y;
                                      }
                              }
                         }
                    }
             }
          lista.set(posicao, lista.get(x));
          lista.set(x, aux);
         } 
      
      
      return lista;
   }
   
   
   
}
