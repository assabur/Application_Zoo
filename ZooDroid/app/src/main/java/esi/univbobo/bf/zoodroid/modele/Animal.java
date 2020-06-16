package esi.univbobo.bf.zoodroid.modele;

//***************************************
// classe animal
//***************************************

public class Animal
{
    private int idAnimal;
    private String pseudo ="";
    private String espece="";
    private byte [] idPhoto;

    public Animal(int idAnimal, String pseudo, String espece, byte[] idPhoto) {
        this.idAnimal = idAnimal;
        this.pseudo = pseudo;
        this.espece = espece;
        this.idPhoto = idPhoto;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEspece() {
        return espece;
    }

    public void setEspece(String espece) {
        this.espece = espece;
    }

    public byte[] getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(byte [] idPhoto) {
        this.idPhoto = idPhoto;
    }
}
