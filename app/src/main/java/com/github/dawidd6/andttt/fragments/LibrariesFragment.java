package com.github.dawidd6.andttt.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.adapters.LibraryAdapter;
import com.github.dawidd6.andttt.misc.Library;

import java.util.ArrayList;

import butterknife.BindView;

public class LibrariesFragment extends BaseFragment {
    public static final String TAG = "LibrariesFragment";
    @BindView(R.id.list) ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_libraries, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Library> array = new ArrayList<>();

        array.add(new Library("EventBus", "Markus Junginger (greenrobot)", "Apache-2.0", "https://github.com/greenrobot/EventBus"));
        array.add(new Library("ButterKnife", "Jake Wharton", "Apache-2.0", "https://github.com/JakeWharton/butterknife"));
        array.add(new Library("Protocol Buffers", "Google Inc.", "Custom MIT", "https://github.com/protocolbuffers/protobuf"));
        array.add(new Library("Constraint Layout", "Google Inc.", "Apache-2.0", "https://android.googlesource.com/platform/frameworks/opt/sherpa/+/studio-3.2.1/constraintlayout"));

        LibraryAdapter adapter = new LibraryAdapter(requireContext(), array);
        list.setAdapter(adapter);
    }
}
