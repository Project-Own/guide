package com.example.guide.interfaces;

import com.example.guide.Modal.Geofence.MyLatLng;

import java.util.List;

public interface IOnLoadLocationListener {
    void onLoadLocationSucess(List<MyLatLng> latLngs);

    void onLoadLocationFailed(String message);
}
