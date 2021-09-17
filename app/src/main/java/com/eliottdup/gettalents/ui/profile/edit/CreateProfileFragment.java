package com.eliottdup.gettalents.ui.profile.edit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.skills.CreateSkillsFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class CreateProfileFragment extends Fragment {

    private FragmentManager fragmentManager;
    private CreateSkillsFragment createSkillsFragment;

    private TextInputLayout layoutPseudo;
    private TextInputEditText inputPseudo;

    private MaterialCardView birthdayCard;


    private Button validateButton ;
    private Button passButton;

    public CreateProfileFragment() {}

    public static CreateProfileFragment newInstance() { return new CreateProfileFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_profile, container, false);

        this.fragmentManager = getParentFragmentManager();
        if(createSkillsFragment == null) createSkillsFragment = CreateSkillsFragment.newInstance();

        this.validateButton = root.findViewById(R.id.validatebutton);
        this.passButton = root.findViewById(R.id.passButton);

        this.layoutPseudo = root.findViewById(R.id.layoutPseudo);
        this.inputPseudo = root.findViewById(R.id.inputPseudo);

        this.birthdayCard = root.findViewById(R.id.container_birthday);

        goToSkills(validateButton);
        goToSkills(passButton);

        setupView();

        return root;
    }

    private void goToSkills(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: send parameters ?
                if(btn ==  validateButton){
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, createSkillsFragment)
                            .addToBackStack(null)
                            .commit();
                }
                //Without parameters ?
                if(btn ==  passButton){
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, createSkillsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    private void setupView() {
        birthdayCard.setOnClickListener(view -> {
            BirthdayDialogFragment birthdayDialogFragment = BirthdayDialogFragment.newInstance();
            birthdayDialogFragment.show(fragmentManager, "birthdayFragment");
        });
    }

}