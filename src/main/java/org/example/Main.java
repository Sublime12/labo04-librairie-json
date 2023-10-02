package org.example;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
//            Ouvrir le fichier et lire tout son contenu
            String contenuCollection = Files.readString(Path.of("collection.json"));

            // Creer l'object
            JSONObject jsonObject = JSONObject.fromObject(contenuCollection);

            JSONArray collection = jsonObject.getJSONArray("collection");

            int nbAlbums = getNombreAlbums(collection);

            System.out.println("Le nombre d'albums est : " + nbAlbums);

            int nbSingles = getNombreSingles(collection);

            System.out.println("Le nombre des singles est : " + nbSingles);

            System.out.println("La liste des albums depuis 2003 est : ");
            ArrayList<String> album2003 = getAlbumsAnnee2003(collection);

            for (int i = 0; i < album2003.size(); i++) {
                System.out.println(i + ") Titre : " + album2003.get(i));
            }

            ArrayList<String> album5Star = getAlbums5Star(collection);
            System.out.println("\nLa liste des albums 5 etoiles est : ");

            for (int i = 0; i < album5Star.size(); i++) {
                System.out.println(i + ") Titre : " + album5Star.get(i));
            }


            System.out.println("Entrer les noms des vos chansons preferees: ");
            Scanner scanner = new Scanner(System.in);
            JSONObject collectionPreferee = JSONObject.fromObject("{}");
//            collectionPreferee.

            for (int i = 0; i < 3; i++) {
                System.out.println("Entrer titre album : ");
                String titre = scanner.nextLine();

                System.out.println("Nom artist : ");
                String artist = scanner.nextLine();

                System.out.println("Date publication : ");
                int datePublication = scanner.nextInt();

                System.out.println("Rating : ");
                int rating = scanner.nextInt();

                Song song = new Song();
                song.setTitle(titre);
                song.setPublication(datePublication);
                song.setArtist(artist);
                song.setType("album");

                collectionPreferee.accumulate("collection", song);
            }

            Files.writeString(Path.of("album-prefere.json"),
                    collectionPreferee.toString());
//            System.out.println(collectionPreferee.toString(4));
//            System.out.println(collection);
        } catch (IOException e) {
            System.out.println("Fichier collection.json n'existe pas");
            System.exit(-1);
        }

    }

    public static int getNombreAlbums(JSONArray collection) {

        int nbAlbums = 0;
        for (int i = 0; i < collection.size(); i++) {
            JSONObject songObject = collection.getJSONObject(i);

            if (songObject.getString("type").equals("album")) nbAlbums++;
        }

        return nbAlbums;
    }
    public static int getNombreSingles(JSONArray collection) {

        int nbSingles = 0;
        for (int i = 0; i < collection.size(); i++) {
            JSONObject songObject = collection.getJSONObject(i);

            if (songObject.getString("type").equals("single")) nbSingles++;
        }

        return nbSingles;
    }

    public static ArrayList<String> getAlbumsAnnee2003(JSONArray collection) {

        ArrayList<String> songs = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            JSONObject songObject = collection.getJSONObject(i);

            if (
                    songObject.getString("type").equals("album") &&
                            songObject.getInt("publication") >= 2003
            ) {
                songs.add(songObject.getString("title"));
            }
        }

        return songs;
    }
    public static ArrayList<String> getAlbums5Star(JSONArray collection) {

        ArrayList<String> songs = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            JSONObject songObject = collection.getJSONObject(i);

            if (
                    songObject.getString("type").equals("album") &&
                            songObject.getInt("rating") == 5
            ) {
                songs.add(songObject.getString("title"));
            }
        }

        return songs;
    }
}