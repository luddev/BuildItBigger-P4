package show.joketeller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JokeFragment extends Fragment {

    public JokeFragment()   {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_joke, container, false);
        TextView jokeText = (TextView) rootView.findViewById(R.id.joke_text);
        String joke = getActivity().getIntent().getStringExtra("JOKE");
        jokeText.setText(joke);
        return rootView;
    }

}
