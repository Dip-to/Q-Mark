package com.example.q_mark.Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q_mark.Adapter.File_show_adapter;
import com.example.q_mark.Adapter.notification_adapter;
import com.example.q_mark.Model.Files;
import com.example.q_mark.Model.Notification;
import com.example.q_mark.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.PriorityQueue;

public class button_srch_content extends Fragment {

    ArrayList<Files> list=new ArrayList<>();
    RecyclerView rv;
    EditText srch;
    File_show_adapter ff;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv=getView().findViewById(R.id.RV);
        ff= new File_show_adapter(getContext(),list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(ff);
        srch=getView().findViewById(R.id.searchbar);
        FirebaseDatabase.getInstance().getReference().child("Upload").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Files files=dataSnapshot.getValue(Files.class);
                    list.add(files);
                }
                ff.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        srch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                srch(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }

    private void computeLPSArray(String pat, int M, int lps[]) {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else // (pat[i] != pat[len])
            {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0) {
                    len = lps[len - 1];

                    // Also, note that we do not increment
                    // i here
                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }


    private ArrayList<Files> getSortedArrayList(ArrayList<Files> tmplist, String pat)
    {
        ArrayList<Pair<Integer,Integer>>tmp = new ArrayList<Pair<Integer,Integer>>();
        int M = pat.length();
        int lps[] = new int[M];
        int j = 0;
        computeLPSArray(pat, M, lps);
        for(int ii=0;ii<tmplist.size();ii++)
        {
            String txt = tmplist.get(ii).getHint();
            int N = txt.length();
            int i = 0; // index for txt[]
            while ((N - i) >= (M - j)) {
                if (pat.charAt(j) == txt.charAt(i)) {
                    j++;
                    i++;
                }
                if (j == M) {
                    tmp.add(new Pair<Integer,Integer> (i - j,ii));
                    break;
                }

                // mismatch after j matches
                else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                    // Do not match lps[0..lps[j-1]] characters,
                    // they will match anyway
                    if (j != 0)
                        j = lps[j - 1];
                    else
                        i = i + 1;
                }
            }
        }
        System.out.println("Sort done");
        Collections.sort(tmp, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> t, Pair<Integer, Integer> t1) {
                if(t.first==t1.first)
                {
                    if(t.second<t1.second)
                    {
                        return 1;
                    }
                    else return 0;
                }
                if(t.first<=t1.first)
                {
                    return  1;
                }
                return 0;
            }
        });
        ArrayList<Files> ans = new ArrayList<Files>();
        for(int i=0;i<tmp.size();i++)
        {
            ans.add(tmplist.get(tmp.get(i).second));
        }
        return ans;
    }
    private void srch(String s) {

        //serach
        Query query=FirebaseDatabase.getInstance().getReference("Upload").orderByChild("hint");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                ArrayList<Files> tmplist=new ArrayList<>();
                tmplist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Files files = dataSnapshot.getValue(Files.class);
                    if(files.getHint().toLowerCase().contains(s.toLowerCase()))
                    {
                        list.add(files);
                    }
                }
//                tmplist = getSortedArrayList(tmplist,s);
//                for(Files fl : tmplist)
//                {
//                    list.add(fl);
//                }
                ff.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
