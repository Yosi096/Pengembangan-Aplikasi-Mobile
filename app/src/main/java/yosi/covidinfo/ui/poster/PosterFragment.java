package yosi.covidinfo.ui.poster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import yosi.covidinfo.R;

public class PosterFragment extends Fragment implements View.OnClickListener{
    ViewFlipper viewFlipper;
    Button next;
    Button previous;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_poster, container, false);
        viewFlipper = (ViewFlipper)root.findViewById(R.id.viewFlilpper);
        next = (Button)root.findViewById(R.id.next);
        previous = (Button)root.findViewById(R.id.previous);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        getActivity().setTitle("InfoCov");
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view==next){
            viewFlipper.showNext();
        }else if (view==previous){
            viewFlipper.showPrevious();
        }
    }
}
