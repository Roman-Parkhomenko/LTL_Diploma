package info.Parkhomenko.personaldiary.view.callbacks;

import info.Parkhomenko.personaldiary.data.model.Diary;
import info.Parkhomenko.personaldiary.data.model.Section;

public interface ItemClickListener {
    void itemClicked(Diary diary);
    void itemClicked(Section section);
}
