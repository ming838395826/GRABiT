package com.android.spin.logreg.fragment;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.base.util.ToastUtil;
import com.android.base.view.listview.CommonListViewWrapper;
import com.android.base.view.listview.CommonListViewWrapperConfig;
import com.android.base.view.listview.ListItemDecoration;
import com.android.spin.R;
import com.android.spin.logreg.adapter.HobbyListAdapter;
import com.android.spin.logreg.entity.HobbyItemEntity;
import com.taobao.uikit.feature.view.TRecyclerView;
import com.taobao.uikit.feature.view.TTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：注册最后一步
 * (1 =>超市,2 =>时装,3 =>饮食,4 =>教育,5 =>专业服务,6 =>动态娱乐,7 =>静态娱乐 ,
 * ,8 =>运动,9 =>保健,10=>电子,11=>家具用品,12=>汽车,13=>美容护理,14=>医疗)
 */
public class RegisterStepSixthFragment extends BaseRegisterFragment implements View.OnClickListener {

    @Bind(R.id.tre_hobby_list)
    TRecyclerView treHobbyList;
    @Bind(R.id.tbtn_next)
    TTextView tbtnNext;

    private CommonListViewWrapper mHobbyListWrapper;
    private HobbyListAdapter mHobbyListAdapter;

    private final int TYPE_REQUEST_UPDATE_TYPE = 0x00;

    public RegisterStepSixthFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterStepSixthFragment newInstance() {
        RegisterStepSixthFragment fragment = new RegisterStepSixthFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_step_sixth;
    }

    @Override
    public void initView() {

        mHobbyListWrapper = new CommonListViewWrapper();

        if (mHobbyListAdapter == null) {
            mHobbyListAdapter = new HobbyListAdapter(getActivity());
        }

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        treHobbyList.addItemDecoration(new ListItemDecoration(
                getActivity(), LinearLayout.VERTICAL, getResources().getDrawable(R.drawable.list_divider_h10_tra)));
        treHobbyList.addItemDecoration(new ListItemDecoration(
                getActivity(), LinearLayout.HORIZONTAL, getResources().getDrawable(R.drawable.list_divider_h10_tra)));
        CommonListViewWrapperConfig config =
                new CommonListViewWrapperConfig.Builder().setListAdapter(
                        mHobbyListAdapter).setListView(treHobbyList)
                        .setLayoutManager(layoutManager).build();
        mHobbyListWrapper.init(config);

        treHobbyList.setOnItemClickListener(new TRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(TRecyclerView parent, View view, int position, long id) {

                if(getCheckedCount() > 4){
                    ToastUtil.shortShow(getString(R.string.text_most_place_hint));
                    return;
                }
                HobbyItemEntity entity = (HobbyItemEntity) mHobbyListWrapper.getAdapter().getDataList().get(position);
                entity.setChecked(!entity.isChecked());
                mHobbyListWrapper.getAdapter().notifyDataSetChanged();
            }
        });
        mHobbyListWrapper.updateListData(getAllPlace());

    }

    /**
     * 获取已选中的数量
     * @return
     */
    private int getCheckedCount(){
        int count = 0;
        if(mHobbyListWrapper == null){
            return count;
        }
        for(HobbyItemEntity entity : (List<HobbyItemEntity>)mHobbyListWrapper.getAdapter().getDataList()){
             if(entity.isChecked()){
                 count++;
             }
        }
        return count;
    }

    @OnClick(R.id.tbtn_next)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbtn_next:
                checkParams();
                break;
        }
    }

    /**
     * 下一步
     */
    private void checkParams() {
        String tags = "";
        List<HobbyItemEntity> allHobbys = mHobbyListWrapper.getAdapter().getDataList();
        for (int i = 1; i < allHobbys.size() + 1; i++) {
            if (allHobbys.get(i - 1).isChecked()) {
                if (i == 1) {
                    tags = 1 + "";
                } else {
                    tags = tags + "," + i;
                }
            }
        }
        if (tags.length() == 0) {
            ToastUtil.shortShow(getString(R.string.text_empty_place));
            return;
        }
        showLoadDialog();
        Map<String, Object> params = new HashMap<>();
        params.put("tags", tags);
        getPresenter().updateUserInfo(params, TYPE_REQUEST_UPDATE_TYPE);

    }

    /**
     * 获取所有爱好
     *
     * @return
     */
    private List<HobbyItemEntity> getAllHobby() {
        List<HobbyItemEntity> hobbys = new ArrayList<>();
        hobbys.add(new HobbyItemEntity(R.mipmap.supermarket, getString(R.string.text_hobby_Super_Markets)));
        hobbys.add(new HobbyItemEntity(R.mipmap.fashion, getString(R.string.text_hobby_Fashion)));
        hobbys.add(new HobbyItemEntity(R.mipmap.food, getString(R.string.text_hobby_Food)));
        hobbys.add(new HobbyItemEntity(R.mipmap.education, getString(R.string.text_hobby_Academics)));
        hobbys.add(new HobbyItemEntity(R.mipmap.professional_service, getString(R.string.text_hobby_Professional_services)));
        hobbys.add(new HobbyItemEntity(R.mipmap.entertainmentb, getString(R.string.text_hobby_Leisure_services)));
        hobbys.add(new HobbyItemEntity(R.mipmap.entertainmenta, getString(R.string.text_hobby_Entertainments)));
        hobbys.add(new HobbyItemEntity(R.mipmap.sport, getString(R.string.text_hobby_Sports)));
        hobbys.add(new HobbyItemEntity(R.mipmap.healthcare, getString(R.string.text_hobby_Health)));
        hobbys.add(new HobbyItemEntity(R.mipmap.electronics, getString(R.string.text_hobby_Electronics)));
        hobbys.add(new HobbyItemEntity(R.mipmap.home, getString(R.string.text_hobby_SFurniture)));
        hobbys.add(new HobbyItemEntity(R.mipmap.car, getString(R.string.text_hobby_Auto)));
        hobbys.add(new HobbyItemEntity(R.mipmap.beauty, getString(R.string.text_hobby_Beauty_and_Skin_Care)));
        hobbys.add(new HobbyItemEntity(R.mipmap.medical, getString(R.string.text_hobby_Medical_service)));
        return hobbys;
    }

    private List<HobbyItemEntity> getAllPlace() {
        List<HobbyItemEntity> hobbys = new ArrayList<>();
        hobbys.add(new HobbyItemEntity(R.mipmap.supermarket, getString(R.string.text_place_islands_district)));
        hobbys.add(new HobbyItemEntity(R.mipmap.fashion, getString(R.string.text_place_Kwai_Tsing_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.food, getString(R.string.text_place_North_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.education, getString(R.string.text_place_Sai_Kung_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.professional_service, getString(R.string.text_place_Sha_Tin_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.entertainmentb, getString(R.string.text_place_Tai_Po_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.entertainmenta, getString(R.string.text_place_Tsuen_Wan_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.sport, getString(R.string.text_place_Tuen_Mun_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.healthcare, getString(R.string.text_place_Yuen_Long_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.electronics, getString(R.string.text_place_Kowloon_City)));
        hobbys.add(new HobbyItemEntity(R.mipmap.home, getString(R.string.text_place_Kwun_Tong_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.car, getString(R.string.text_place_Sham_Shui_Po_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.beauty, getString(R.string.text_place_Wong_Tai_Sin_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.medical, getString(R.string.text_place_Yau_Tsim_Mong_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.medical, getString(R.string.text_place_Central_and_Western_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.medical, getString(R.string.text_place_Eastern_District)));
        hobbys.add(new HobbyItemEntity(R.mipmap.medical, getString(R.string.text_place_Southern)));
        hobbys.add(new HobbyItemEntity(R.mipmap.medical, getString(R.string.text_place_Wan_Chai_District)));
        return hobbys;
    }

    @Override
    public void onFail(String code, int type) {

    }

    @Override
    public void onComplete(int type) {
        dismissLoadDialog();
    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type){
            case TYPE_REQUEST_UPDATE_TYPE:
                //提交更新成功
                if (mListener != null) {
                    mListener.onFragmentInteraction();
                }
                break;
        }
    }
}
