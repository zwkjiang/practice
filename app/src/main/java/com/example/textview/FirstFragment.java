package com.example.textview;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.common.Api;
import com.example.common.RetrofitManager;
import com.example.common.TeamAMember;
import com.example.common.TeamLeaderMeber;
import com.example.common.TeamMember;
import com.example.common.TeamMemberInvocation;
import com.example.textview.databinding.FragmentFirstBinding;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.clean.setOnClickListener(view1 -> {
            TeamAMember zhang = new TeamAMember("zhang");
            TeamLeaderMeber teamLeaderMeber = new TeamLeaderMeber(zhang);
//            teamLeaderMeber.reviewCode();
            TeamMemberInvocation<TeamMember> teamAMemberTeamMemberInvocation = new TeamMemberInvocation<>(zhang);
            TeamMember instance = (TeamMember)Proxy.newProxyInstance(TeamMember.class.getClassLoader(), new Class<?>[]{TeamMember.class}, teamAMemberTeamMemberInvocation);
            instance.reviewCode();
            HashMap<String, String> ma = new HashMap<>();
            ma.put("","");
            SparseIntArray sparseIntArray = new SparseIntArray();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                sparseIntArray.setValueAt(1,1);
            }
//            MyInterceptor myInterceptor = new MyInterceptor();
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .connectTimeout(10, TimeUnit.SECONDS)
//                    .writeTimeout(10,TimeUnit.SECONDS)
//                    .readTimeout(10,TimeUnit.SECONDS)
//                    .build();
//            MediaType parse = MediaType.parse("application/json;charset=utf-8");
//            RequestBody requestBody = RequestBody.create(parse, "");
//            Uri build = Uri.parse("http://web.juhe.cn/constellation/getAll").buildUpon()
//                    .appendQueryParameter("consName", "天蝎座")
//                    .appendQueryParameter("type","today")
//                    .build();
//            Request request = new Request.Builder()
//                    .url(build.toString())
//                    .header("Content-Type","application/x-www-form-urlencoded")
//                    .build();
//            Call call = client.newCall(request);
                    Observable<Object> dage = RetrofitManager.getInstance().create(Api.class).getData("杭州");
                    dage.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Object>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    Log.i("zhang",d.toString());
                                }

                                @Override
                                public void onNext(Object o) {
                                    Log.i("zhang",o.toString());
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.i("zhang",e.toString());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
        });
    }

    private OkHttpClient createHttp() {
        return new OkHttpClient().newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    class MyInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            return chain.proceed(chain.request());
        }
    }

}