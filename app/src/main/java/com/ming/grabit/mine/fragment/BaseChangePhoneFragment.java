package com.ming.grabit.mine.fragment;

import android.content.Context;

import com.android.base.base.MvpFragment;
import com.android.base.mvp.view.IView;
import com.ming.grabit.mine.presenter.MinePresenter;

public abstract class BaseChangePhoneFragment extends MvpFragment<IView,MinePresenter> {

    protected OnFragmentInteractionListener mListener;

    public BaseChangePhoneFragment() {
        // Required empty public constructor
    }

    @Override
    public MinePresenter initPresenter() {
        return new MinePresenter();
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
