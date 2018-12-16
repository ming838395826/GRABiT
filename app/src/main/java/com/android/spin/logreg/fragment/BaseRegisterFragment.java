package com.android.spin.logreg.fragment;

import android.content.Context;

import com.android.base.base.MvpFragment;
import com.android.base.mvp.view.IView;
import com.android.spin.logreg.presenter.RegisterPresenter;

public abstract class BaseRegisterFragment extends MvpFragment<IView,RegisterPresenter> {

    protected OnFragmentInteractionListener mListener;

    public BaseRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

}
