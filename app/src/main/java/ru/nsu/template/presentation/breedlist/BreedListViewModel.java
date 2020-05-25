package ru.nsu.template.presentation.breedlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.nsu.template.R;
import ru.nsu.template.TemplateApplication;
import ru.nsu.template.data.model.Avatar;
import ru.nsu.template.data.model.Breed;
import ru.nsu.template.data.model.BreedList;
import ru.nsu.template.data.network.DogApi;
import ru.nsu.template.data.network.DogApiClient;

public class BreedListViewModel extends ViewModel {

    private DogApi api;

    private MutableLiveData<String> headerLiveData = new MutableLiveData<>();
    LiveData<String> observeHeaderLiveData() {
        return headerLiveData;
    }

    private MutableLiveData<List<Breed>> breedsLiveData = new MutableLiveData<>();
    LiveData<List<Breed>> observeBreedsLiveData() {
        return breedsLiveData;
    }

    //LiveData<String> observeErrorLiveData() {
    //    return errorLiveData;
    //}
    //private MutableLiveData<String> errorLiveData = new MutableLiveData<>("");

    LiveData<Boolean> observeIsLoadingLiveData() {
        return isLoadingLiveData;
    }
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>(false);

    public BreedListViewModel() {
        api = DogApiClient.getClient(TemplateApplication.getInstance()).create(DogApi.class);
        breedsLiveData.setValue(new ArrayList<>());
        init();
    }

    private void init() {
        isLoadingLiveData.setValue(true);
        api.getBreeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<BreedList>() {
                    @Override
                    public void onSuccess(BreedList breeds) {
                        getPicture(breeds.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingLiveData.setValue(false);
                    }
                });
    }

    private void getPicture(List<String> breedNames) {
        for (String breedName : breedNames) {
            getRandomPicture(breedName);
        }
    }

    private void getRandomPicture(String name) {
        isLoadingLiveData.setValue(true);
        api.getRandomBreedImage(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Avatar>() {
                    @Override
                    public void onSuccess(Avatar avatar) {
                        List<Breed> breed = breedsLiveData.getValue();
                        Objects.requireNonNull(breed).add(new Breed(name, avatar));
                        breedsLiveData.setValue(breed);
                        isLoadingLiveData.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingLiveData.setValue(false);
                    }
                });
    }
}
