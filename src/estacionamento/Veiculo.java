
package estacionamento;

import java.util.ArrayList;
import java.io.Serializable;

public class Veiculo implements Serializable {
    
    private String modelo;
    private float peso;
    private float comprimento;
    private float largura;
    private float altura;
    private int chassi;

    public Veiculo(String modelo, int chassi, float peso, float altura, float comprimento, float largura) {
        this.modelo = modelo;
        this.peso = peso;
        this.comprimento = comprimento;
        this.largura = largura;
        this.altura = altura;
        this.chassi = chassi;
    }

    public Veiculo() {
    }
    
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getComprimento() {
        return comprimento;
    }

    public void setComprimento(float comprimento) {
        this.comprimento = comprimento;
    }

    public float getLargura() {
        return largura;
    }

    public void setLargura(float largura) {
        this.largura = largura;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public int getChassi() {
        return chassi;
    }

    public void setChassi(int chassi) {
        this.chassi = chassi;
    }
    
    //Carrega para um Array List a lista de carros presente na tabela.
    public static ArrayList<Veiculo> carregaDaTabelaCarros(Visual visual){
        ManipulaTabelas manip = new ManipulaTabelas(visual,null);
        int tamanhoCarros= manip.getTamanhoTabelaCarros();
       
            ArrayList<Veiculo> listaCarros = new ArrayList();
                for(int x=0;x<tamanhoCarros;x++){
                listaCarros.add(manip.getLinhaTabelaCarros(x)); 
                }
    
    return listaCarros;
    }
    
    //Procura e retorna, se possível,a primeira vaga que comporte o veiculo.
    public static Vaga procuraMelhorVaga(Veiculo veiculo){
            
            Relatorio vagas = new Relatorio();
            vagas.getInstance();
            ArrayList<Vaga> listaVagas = vagas.getListaVagas();

                for(Vaga X: listaVagas){
                    if(veiculo.getAltura() <= X.getLimiteAltura() && 
                    veiculo.getComprimento() <= X.getLimiteComprimento() && 
                    veiculo.getLargura() <= X.getLimiteLargura() && 
                    veiculo.getPeso() <= X.getLimitePeso() &&
                    X.isDisponivel())
                    return X;  
                }
    
        return null;
        }
    
}
