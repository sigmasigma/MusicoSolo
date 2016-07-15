package com.example.gushimakota.musicosolo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

public class EditFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    private String mPart;
    private String mParam2;
    private int mState;
    private String mPartObjectId;

    private MediaPlayer player;
    private int resId;
    private String strIdea;
    private String ideaPath;

    private boolean playing;
    private boolean init;

    private int idea[]={0,0,0,0,1};

    private TextView title;
    private Button play;
    private Button register;
//    private ParseUser user;
    private EditListener mListener;

    public EditFragment() {
        // Required empty public constructor
    }

    public interface EditListener {
        public void onClickButton(String idea);
    }

    public static EditFragment newInstance(String param1) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playing = false;
        init = false;
        if (getArguments() != null) {
            mPart = getArguments().getString(ARG_PARAM1);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit, container, false);
        setSpinners(v);
        title = (TextView)v.findViewById(R.id.titleText);
        title.setText(mPart + " part");
        setEditButtonAction(v);
        return v;
    }

    public void setSpinners(View v){

        //spinnerの処理
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter1.add("なし");
        adapter1.add("4つ打");
        adapter1.add("2分打");
        adapter1.add("ドンドド");
        adapter1.add("ドンドッド");
//        adapter1.add("ドンドッド２");
        Spinner  kick= (Spinner) v.findViewById(R.id.kick_spin);
        // アダプター設定
        kick.setAdapter(adapter1);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        kick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                idea[0] = position;
                init = false;
                if (playing){
                    player.stop();
                    player.release();
                    playing = false;

                    play.setText("この部分を再生");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //spinnerの処理
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter2.add("なし");
        adapter2.add("頭");
        adapter2.add("2拍4拍");
        adapter2.add("3拍");
        Spinner  snare= (Spinner) v.findViewById(R.id.snare_spin);
        // アダプター設定
        snare.setAdapter(adapter2);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        snare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                idea[1] = position;
                init = false;
                if (playing){
                    player.stop();
                    player.release();
                    playing = false;

                    play.setText("この部分を再生");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //spinnerの処理
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter3.add("なし");
        adapter3.add("クローズ4分");
        adapter3.add("クローズ8分");
//        adapter3.add("クローズ16分");
        adapter3.add("オープン4分");
//        adapter3.add("オープン8分");
        adapter3.add("クローズ裏打ち");
        adapter3.add("オープン裏打ち");

        Spinner hihat= (Spinner) v.findViewById(R.id.hihat_spin);
        // アダプター設定
        hihat.setAdapter(adapter3);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        hihat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position){
                    case 0:
                        idea[2] = 0;
                        break;
                    case 1:
                        idea[2] = 1;
                        break;
                    case 2:
                        idea[2] = 2;
                        break;
                    case 3:
                        idea[2] = 4;
                        break;
                    case 4:
                        idea[2] = 6;
                        break;
                    case 5:
                        idea[2] = 7;
                        break;
                }
                init = false;
                if (playing){
                    player.stop();
                    player.release();
                    playing = false;

                    play.setText("この部分を再生");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //spinnerの処理
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter4.add("なし");
        adapter4.add("カノン進行");
        adapter4.add("6451進行");
        adapter4.add("4151進行");
//        adapter4.add("456進行");
//        adapter4.add("6365進行");
        Spinner chord= (Spinner) v.findViewById(R.id.chord);
        // アダプター設定
        chord.setAdapter(adapter4);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        chord.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                idea[3]=position;
                init = false;
                if (playing){
                    player.stop();
                    player.release();
                    playing = false;

                    play.setText("この部分を再生");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //spinnerの処理
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter5.add("全音符");
        adapter5.add("ノリ良い");
        Spinner pattern= (Spinner) v.findViewById(R.id.pattern);
        // アダプター設定
        pattern.setAdapter(adapter5);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        pattern.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                idea[4]=position+1;
                if (idea[3]==0){
                    idea[4]=1;
                }
                init = false;
                if (playing){
                    player.stop();
                    player.release();
                    playing = false;

                    play.setText("この部分を再生");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void setEditButtonAction(View v){
        play = (Button)v.findViewById(R.id.play);
        register = (Button) v.findViewById(R.id.register);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!init){
                    Resources res = getResources();
                    switch (mPart) {
                        case "A":
                            strIdea = "a" + String.valueOf(idea[0]) + String.valueOf(idea[1]) + String.valueOf(idea[2]) + String.valueOf(idea[3]) + String.valueOf(idea[4]);
//                            resId = res.getIdentifier(strIdea, "raw", getActivity().getPackageName());
                            break;
                        case "B":
                            strIdea = "b" + String.valueOf(idea[0]) + String.valueOf(idea[1]) + String.valueOf(idea[2]) + String.valueOf(idea[3]) + String.valueOf(idea[4]);
                            break;
                        case "C":
                            strIdea = "c" + String.valueOf(idea[0]) + String.valueOf(idea[1]) + String.valueOf(idea[2]) + String.valueOf(idea[3]) + String.valueOf(idea[4]);
                            break;
                        default:
                            return;
                    }
                    player = new MediaPlayer();
                    ideaPath = Environment.getExternalStorageDirectory().getPath()+"/Musico/"+strIdea+".mp3";
                    Log.d("a",ideaPath);
                    try {
                        player.setDataSource(ideaPath);
                        player.prepare();

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    init = true;
                }
                if (!playing) {


//                    resId = res.getIdentifier(strIdea, "raw", getActivity().getPackageName());
//                    player = MediaPlayer.create(getActivity(), resId);
//                    player.start();
                    player.start();
                    play.setText("停止");
                    playing = true;
                } else {
                    player.pause();
                    play.setText("この部分を再生");
                    playing = false;
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog(){
        // 確認ダイアログの生成
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(getContext());
        alertDlg.setTitle("この内容で送信します");
        alertDlg.setMessage("よろしいですか？");


//        user.put(mPart + String.valueOf(mState), true);
        alertDlg.setPositiveButton(
                "送信する",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // OK ボタンクリック処理
                        switch (mPart) {
                            case "A":
                                strIdea = "a" + String.valueOf(idea[0]) + String.valueOf(idea[1]) + String.valueOf(idea[2]) + String.valueOf(idea[3]) + String.valueOf(idea[4]);
//                            resId = res.getIdentifier(strIdea, "raw", getActivity().getPackageName());
                                break;
                            case "B":
                                strIdea = "b" + String.valueOf(idea[0]) + String.valueOf(idea[1]) + String.valueOf(idea[2]) + String.valueOf(idea[3]) + String.valueOf(idea[4]);
                                break;
                            case "C":
                                strIdea = "c" + String.valueOf(idea[0]) + String.valueOf(idea[1]) + String.valueOf(idea[2]) + String.valueOf(idea[3]) + String.valueOf(idea[4]);
                                break;
                            default:
                                return;
                        }
                        if (playing){
                            player.stop();
                            player.release();
                        }
                        mListener.onClickButton(strIdea);
                    }
                });
        alertDlg.setNegativeButton(
                "やり直す",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel ボタンクリック処理
                        return;
                    }
                });

        // 表示
        alertDlg.create().show();
    }

    // FragmentがActivityに追加されたら呼ばれるメソッド
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // APILevel23からは引数がActivity->Contextになっているので注意する

        // contextクラスがMyListenerを実装しているかをチェックする
        if (context instanceof EditListener) {
            // リスナーをここでセットするようにします
            mListener = (EditListener) context;
        }

    }

    // FragmentがActivityから離れたら呼ばれるメソッド
    @Override
    public void onDetach() {
        super.onDetach();
        // 画面からFragmentが離れたあとに処理が呼ばれることを避けるためにNullで初期化しておく
        mListener = null;
    }

    private void trackIdeaSet(String part){
    }

    private void parseStateForward(String id){
    }
}