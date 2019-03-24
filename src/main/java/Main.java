import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void JsonReading(String fileName)
    {
        String result = "";
        try {
            final FileWriter csvPerson = new FileWriter("persons.csv");
            final FileWriter csvRating = new FileWriter("ratings.csv");
            final FileWriter csvMovie = new FileWriter("movies.csv");
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            JSONParser parser = new JSONParser();


            // We edit the first line of each document
            csvPerson.write("idP,name,gender,occupation,age");
            csvPerson.write("\n");

            csvRating.write("oid,idP,idM,rating,timestamp");
            csvRating.write("\n");

            csvMovie.write("idM,title");
            csvMovie.write("\n");

            List<String> names = new ArrayList<String>();
            List<String>  movies = new ArrayList<String>();


            int idPerson = 0;
            while (line != null) {
                StringBuilder correctedLine = new StringBuilder();

                JSONObject jsonLine = (JSONObject) parser.parse(line);

                JSONObject idMovieLensObj = new JSONObject();

                idMovieLensObj = (JSONObject)jsonLine.get("_id");


                String oid = "\"" + (String)idMovieLensObj.get("$oid") + "\"";

                String age = String.valueOf(jsonLine.get("age"));

                String gender = "\"" + (String)jsonLine.get("gender") + "\"";

                String name = "\"" + (String)jsonLine.get("name") +"\"";

                String occupation = "\"" + (String) jsonLine.get("occupation") + "\"";



                JSONObject movie = new JSONObject();

                movie = (JSONObject) jsonLine.get("movie");

                String movieId = "\"" + String.valueOf(movie.get("id")) + "\"";

                String movieRating = "\"" + String.valueOf(movie.get("rating")) + "\"";

                String movieTimestamp = "\"" + String.valueOf(movie.get("timestamp")) + "\"";

                String movieTitle = "\"" + (String) movie.get("title") + "\"";



                int indexNames = 0 ;
                for(int  i = 0 ; i < names.size() ; i++) {
                    if (names.get(i).equals(name)) {
                        indexNames ++;
                    }
                }

                if ( indexNames == 0){
                        names.add(name);
                        idPerson ++ ;
                        String insertPerson = idPerson + "," + name +  "," + gender + "," + occupation + "," + age ;
                        csvPerson.write(insertPerson.toString());
                        csvPerson.write("\n");
                }

                String insertRating = oid + "," + idPerson + "," + movieId + "," + movieRating + "," + movieTimestamp ;
                csvRating.write(insertRating.toString());
                csvRating.write("\n");

                int indexMovies = 0 ;
                for( String i : movies) {
                    if (i.equals(movieId)) {
                        indexMovies ++;
                    }
                }

                if ( indexMovies == 0){
                    movies.add(movieId);

                    String insertMovie = movieId + "," + movieTitle ;
                    csvMovie.write(insertMovie.toString());
                    csvMovie.write("\n");

                }


                line = br.readLine();


            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        JsonReading("MovieLens_ratingUsers.json");

    }
}
