package com.example.guide.ui.forex;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.guide.Model.ForexModel;
import com.example.guide.R;
import com.example.guide.adapters.ForexAdapter;
import com.example.guide.databinding.DialogCustomLayoutBinding;
import com.example.guide.databinding.ForexFragmentBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ForexFragment extends Fragment {

    private ForexViewModel mViewModel;
    private FrameLayout forexFrameLayout;
    private EditText editText;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Spinner convertToSpinner;
    private Spinner convertFromSpinner;
    private FloatingActionButton conversionButton;
    private Button convertButton;
    private TextView textView;
    private LinearLayout convertLinearLayout;
    private EditText convertEditText;
    private ForexFragmentBinding binding;
    private FloatingActionButton fab;
    private DialogCustomLayoutBinding customLayoutBinding;
    private View dialogView;
    private ShimmerFrameLayout shimmerFrameLayout;
    public static ForexFragment newInstance() {
        return new ForexFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.bind(inflater.inflate(R.layout.forex_fragment, container, false));

        dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
        customLayoutBinding = DataBindingUtil.bind(dialogView);

        View view = binding.getRoot();
        forexFrameLayout = view.findViewById(R.id.forexFrameLayout);
        recyclerView = view.findViewById(R.id.forexRecyclerView);
        //   forexProgressBar = findViewById(R.id.forexProgressBar);
        //   forexProgressBar.setVisibility(View.VISIBLE);
        editText= view.findViewById(R.id.Edit);

        shimmerFrameLayout = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeColors(Color.CYAN, Color.RED, Color.BLUE, Color.BLACK);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        convertFromSpinner = view.findViewById(R.id.spinnerForexFrom);
        convertToSpinner = view.findViewById(R.id.spinnerForexTo);
        convertLinearLayout = view.findViewById(R.id.converterView);
        conversionButton = view.findViewById(R.id.conversionButton);
        convertButton = view.findViewById(R.id.convertButton);
        textView = view.findViewById(R.id.convertResult);
        convertEditText = view.findViewById(R.id.convertNumber);
        fab = view.findViewById(R.id.conversionButton);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ForexViewModel.class);

        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        customLayoutBinding.setViewModel(mViewModel);
        customLayoutBinding.setLifecycleOwner(this);

        ForexModel forexModels = new ForexModel();
        ForexAdapter forexAdapter = new ForexAdapter(forexModels, recyclerView, getContext());
        recyclerView.setAdapter(forexAdapter);


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if(mViewModel.isClicked.getValue()) {
                    mViewModel.isClicked.setValue(false);
                }else {
                    Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment).navigateUp();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        mViewModel.loadForexData().observe(this, new Observer<ForexModel>() {
            @Override
            public void onChanged(ForexModel forexModels) {
                forexAdapter.filterList(forexModels);
              //  Toast.makeText( getActivity(), forexModels.getPairList().toString() , Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.loadSpinnerData().observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(String[] s) {

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, s);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                convertFromSpinner.setAdapter(arrayAdapter);
                convertToSpinner.setAdapter(arrayAdapter);

            }
        });




    }

}

