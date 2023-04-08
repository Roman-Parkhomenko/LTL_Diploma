package info.Parkhomenko.personaldiary.view.callbacks;

import info.Parkhomenko.personaldiary.data.model.Section;

public interface SectionStateChangeListener {
    void onSectionStateChanged(Section section, boolean isOpen);
}
