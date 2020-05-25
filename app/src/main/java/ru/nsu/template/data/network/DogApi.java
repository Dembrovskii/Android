package ru.nsu.template.data.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.nsu.template.data.model.Avatar;
import ru.nsu.template.data.model.BreedList;

public interface DogApi {
    @GET("/api/breeds/list")
    Single<BreedList> getBreeds();

    @GET("/api/breed/{breed}/images/random")
    Single<List<Avatar>> getPicture(@Path("breed") String breed);

    @GET("/api/breed/{breed}/list")
    Single<BreedList> getSubBreeds(@Path("breed") String breed);

    @GET("/api/breed/{breed}/images/random")
    Single<Avatar> getRandomBreedImage(@Path("breed") String breed);

    @GET("/api/breed/{breed}/{subBreed}/images/random")
    Single<Avatar> getRandomSubBreedImage(@Path("breed") String breed, @Path("subBreed") String subBreed);
}
