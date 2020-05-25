package ru.nsu.template.presentation.breed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.nsu.template.TemplateApplication;
import ru.nsu.template.data.model.Avatar;
import ru.nsu.template.data.model.Breed;
import ru.nsu.template.data.model.BreedList;
import ru.nsu.template.data.network.DogApi;
import ru.nsu.template.data.network.DogApiClient;

public class BreedViewModel extends ViewModel {
    private String breed;

    private DogApi api;

    public LiveData<List<Breed>> observeSubBreedLiveData() { return subBreedLiveData; }
    private MutableLiveData<List<Breed>> subBreedLiveData = new MutableLiveData<>();

    //public LiveData<Breed> observeUserLiveData() { return userLiveData; }
   // private MutableLiveData<Breed> userLiveData = new MutableLiveData<>();

    public LiveData<Boolean> observeIsLoadingLiveData() { return isLoadingLiveData; }
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>(false);

    public BreedViewModel(String breed) {
        this.breed = breed;

        // todo make api singleton
        api = DogApiClient.getClient(TemplateApplication.getInstance()).create(DogApi.class);

        init();
    }

    private void init() {
        isLoadingLiveData.setValue(true);
        api.getSubBreeds(breed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<BreedList>() {
                    @Override
                    public void onSuccess(BreedList breeds) {
                        isLoadingLiveData.setValue(false);
                        getPictures(breed, breeds.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingLiveData.setValue(false);
                    }
                });
    }

    private void getPictures(String breed, List<String> breedNames) {
        for (String subBreed : breedNames) {
            getRandomPicture(breed, subBreed);
        }
    }

    private void getRandomPicture(String breed, String subBreed) {
        isLoadingLiveData.setValue(true);
        api.getRandomSubBreedImage(breed, subBreed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Avatar>() {
                    @Override
                    public void onSuccess(Avatar avatar) {
                        List<Breed> breeds = subBreedLiveData.getValue();
                        Objects.requireNonNull(breeds).add(new Breed(subBreed, avatar));

                        subBreedLiveData.setValue(breeds);
                        isLoadingLiveData.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingLiveData.setValue(false);
                    }
                });
    }
}
