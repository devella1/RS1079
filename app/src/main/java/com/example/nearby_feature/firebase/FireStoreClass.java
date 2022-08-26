package com.example.nearby_feature.firebase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.nearby_feature.activities.Missing_banks;
import com.example.nearby_feature.newPlace;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreClass {

    private FirebaseFirestore db;



    public static void addMissingBank(String Name, String descripton, String latitude, String longitude, String type, Missing_banks activity, boolean isAdmin) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> missingBank = new HashMap<>();
        missingBank.put("Name", Name);
        missingBank.put("Desc", descripton);
        missingBank.put("lat", latitude);
        missingBank.put("lon", longitude);





        if(isAdmin){
            db.collection(type)
                    .add( missingBank)
                    .addOnSuccessListener(
                            activity.addSuccess()
                    ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                        }
                    });
        }
        else{
            db.collection("missingBanks")
                    .add( missingBank)
                    .addOnSuccessListener(
                            activity.addSuccess()
                    ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                        }
                    });
        }

    }


    public static String getCurrentUserID() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String currentUserID = "";

        if (currentUser != null) {
            currentUserID = currentUser.getUid();
        }
        return currentUserID;
    }

    public static  List<newPlace> getNearBy(){
//        CollectionReference DsRef = db.collection("dataset");
//        //Query query = citiesRef.whereEqualTo("state", "CA");
        List<newPlace> ds = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("dataset")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                newPlace np =document.toObject(newPlace.class);
//                                ds.add(np);

                                newPlace np =new newPlace("hello","31.25809249169579","75.70791196078062","24/7","Atm");

                                Log.d("TAG", document.getId() + " => " + document.getData());
                                Log.d("TAG", np.desc);
                            }
                        } else {

                        }
                    }
                });
        Log.d("TAG", String.valueOf(ds.size()));
        return ds;
    }

//    public void setDocument(String currentUserID) {
//
//        Map<String, Object> city = new HashMap<>();
//        city.put("name", "Los Angeles");
//        city.put("state", "CA");
//        city.put("country", "USA");
//
//        db.collection("cities").document("LA")
//                .set(city)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully written!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error writing document", e);
//                    }
//                });

//
    public static void updateFavs(String currentUserID,String placeId ){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("favourites").document(currentUserID)
                .update("favs", FieldValue.arrayUnion(placeId));
    }

    public static void addFirstTime(String currentUser,String placeId){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, ArrayList<String>> favs = new HashMap<>();
        ArrayList<String> curfav = new ArrayList<String>();
        curfav.add(placeId);
        favs.put("favs", curfav);

        db.collection("favourites")
                .document(currentUser)
                .set(favs);
    }

//
    public static void addToFav(String currentUserID ,String placeId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();



//
//        DocumentReference docRef = db.collection("items").document("your_id");
//        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(DocumentSnapshot snap, FirestoreException fe) {
//                if (snap.exists()) {
//                    //update
//                } else {
//                    //Insert
//                }
//            }
//
//        });;
//
//        if(!cur_doc){
//            addFirstTime(currentUserID,placeId);
//        }
//        else{
//            updateFavs(currentUserID,placeId);
//        }



    }




}
