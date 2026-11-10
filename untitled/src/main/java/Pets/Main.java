package Pets;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Pet pet = new Pet(65502291, "Paçoca", "gato", "caça o laser");

        System.out.println(pet.getNome());
        System.out.println(pet.getChaveNome());
    }
}