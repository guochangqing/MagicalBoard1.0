package com.magicalboard.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omusic.magicalboard.R;

public class DrawboardFragment extends Fragment{
	@Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
            Bundle savedInstanceState) { 
        // TODO Auto-generated method stub 
        return inflater.inflate(R.layout.fragment_drawboard, container,false); 
    } 
}
