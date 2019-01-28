/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estacionamento;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public class ManipulaTabelas {
    static Visual visual;
    static VisualSair visual2;
    
    public ManipulaTabelas(Visual vis,VisualSair visu){
        visual = vis;
        visual2 = visu;
    }
    
    public int getTamanhoTabelaCarros(){
        return visual.tabelaCarros.getRowCount();
    }
    
    public int getTamanhoTabelaVagas(){
        return visual.tabelaCarros.getRowCount();
    }
    
    //Retorna veiculo que esta na tabela de carros na linha x especificada.
    public Veiculo getLinhaTabelaCarros(int x){
        String modelo = visual.tabelaCarros.getValueAt(x,0).toString();
        int chassi = Integer.valueOf(visual.tabelaCarros.getValueAt(x,1).toString());
        float peso = Float.valueOf(visual.tabelaCarros.getValueAt(x,2).toString());
        float altura = Float.valueOf(visual.tabelaCarros.getValueAt(x,3).toString());
        float comp = Float.valueOf(visual.tabelaCarros.getValueAt(x,4).toString());
        float larg = Float.valueOf(visual.tabelaCarros.getValueAt(x,5).toString());
        Veiculo novo = new Veiculo(modelo,chassi,peso,altura,comp,larg);
        
        return novo;
    }
    //retorna vaga que está na tabela de vagas na linha x especificada.
    public Vaga getLinhaTabelaVagas(int x){
        boolean disp;
        int ID = Integer.valueOf(visual.tabelaVagas.getValueAt(x,0).toString());
        float peso = Float.valueOf(visual.tabelaVagas.getValueAt(x,1).toString());
        float altura = Float.valueOf(visual.tabelaVagas.getValueAt(x,2).toString());
        float comp = Float.valueOf(visual.tabelaVagas.getValueAt(x,3).toString());
        float larg = Float.valueOf(visual.tabelaVagas.getValueAt(x,4).toString());
         if(visual.tabelaVagas.getValueAt(x,5).toString().equals("Sim"))
           disp = true;
         else
          disp = false;
       
        Vaga vaga = new Vaga(ID,peso,altura,comp,larg,disp);
        
        return vaga;
    }
    
    //Deleta carro da tabela da carros após estacionado.
    public void deletaLinhaCarro(int linha){
       
       DefaultTableModel model = (DefaultTableModel) visual.tabelaCarros.getModel();
       model.removeRow(linha);
       
    }
    
    //Muda para "Não" na disponibilidade da vaga .
    public void trancaVaga(int linha){
        DefaultTableModel model = (DefaultTableModel) visual.tabelaVagas.getModel();
        
        Relatorio tranca = new Relatorio();
        tranca.getInstance();
        tranca.getListaVagas().get(linha).setDisponivel(false);
        
        model.setValueAt("Não", linha, 5);
    }
    
    //Muda para "SIm" na disponibilidade da vaga.
    public void liberaVaga(int linha){
        DefaultTableModel model = (DefaultTableModel) visual.tabelaVagas.getModel();
        
        Relatorio tranca = new Relatorio();
        tranca.getInstance();
        tranca.getListaVagas().get(linha).setDisponivel(true);
        
        model.setValueAt("Sim", linha, 5);
    }
    
    //Quando escolhida a opção Sair no menu principal, uma nova janela é carregada com uma tabela com todas as vagas ocupadas, e esse método preenche essa tabela.
    public void preencheTabelaSair(VisualSair visual){
        Relatorio vagas = new Relatorio();
        Veiculo veiculo=null;
        vagas.getInstance();
        DefaultTableModel sair = (DefaultTableModel) visual.tabelaSair.getModel();
        for(Vaga X: vagas.getListaVagas()){
            if(!X.isDisponivel()){
                veiculo = vagas.getCarroEstacionado(X.getID());
                sair.addRow(new String[] {String.valueOf(X.getID()),String.valueOf(X.getLimitePeso()),String.valueOf(X.getLimiteAltura()),
                            String.valueOf(X.getLimiteComprimento()),String.valueOf(X.getLimiteLargura()),veiculo.getModelo()+" ("+veiculo.getChassi()+")"});
            }
        } 
    }
    
    //Retorna lista de carros que estão na fila.
    public ArrayList<Veiculo> getListaCarros(){
        ArrayList<Veiculo> lista = new ArrayList();
        int tamanho= getTamanhoTabelaCarros();
        
        for(int x=0;x<tamanho;x++){
            lista.add(getLinhaTabelaCarros(x));
        }
        
        return lista;
    }
    
    //Preenche tabela de carros na janela principal.
    public void preencheCarros(ArrayList<Veiculo> listaVeiculos){
           DefaultTableModel infoCarro = (DefaultTableModel) visual.tabelaCarros.getModel();
           
           while(infoCarro.getRowCount()>0){
            infoCarro.removeRow(0);
            }
           
           for(Veiculo X: listaVeiculos){   
            infoCarro.addRow(new String[] {X.getModelo(),String.valueOf(X.getChassi()),String.valueOf(X.getPeso()),String.valueOf(X.getAltura()),String.valueOf(X.getComprimento()),String.valueOf(X.getLargura())});
            
           }
           
           
    }
    //preenche tabela de vagas na janela principal.
    public void preencheVagas(){
        DefaultTableModel infoVaga = (DefaultTableModel) visual.tabelaVagas.getModel();
        Relatorio rel = new Relatorio();
        rel.getInstance();
        ArrayList<Vaga> vagas = rel.getListaVagas();
        
        while(infoVaga.getRowCount()>0){
        infoVaga.removeRow(0);
        }
        
        for(Vaga Y: vagas){   
               String disp;
               if(Y.isDisponivel())
                   disp = "Sim";
               else
                   disp = "Não";
               infoVaga.addRow(new String[] {String.valueOf(Y.getID()),String.valueOf(Y.getLimitePeso()),String.valueOf(Y.getLimiteAltura()),String.valueOf(Y.getLimiteComprimento()),String.valueOf(Y.getLimiteLargura()),disp});
            }
        
    }
}
