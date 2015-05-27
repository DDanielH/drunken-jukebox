package de.whs.drunkenjukebox.client.admin.view;

import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SongListViewImpl extends Composite implements SongListView {

	private final TextBox textBoxSearch = new TextBox();
	private final ListBox listBoxSongs = new ListBox();
	private final Button buttonCreate = new Button("Erstellen");

	public SongListViewImpl() {
		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(8);

		textBoxSearch.getElement().setPropertyString("placeholder", "Suche...");
		panel.add(textBoxSearch);

		listBoxSongs.setVisibleItemCount(10);
		listBoxSongs.setWidth("100%");
		panel.add(listBoxSongs);

		panel.add(buttonCreate);
		panel.setCellHorizontalAlignment(buttonCreate,
				HasHorizontalAlignment.ALIGN_RIGHT);

		// Wrap the content in a DecoratorPanel
		DecoratorPanel decPanel = new DecoratorPanel();
		decPanel.setWidget(panel);
		initWidget(decPanel);
	}

	@Override
	public void setSongs(List<String> songs) {
		listBoxSongs.clear();
		for (String s : songs)
			listBoxSongs.addItem(s);
		 
		if (songs.size() > 0)
			setSelectedIndex(0);
	}

	@Override
	public HasChangeHandlers getSongsListBox() {
		return listBoxSongs;
	}

	@Override
	public int getSelectedIndex() {
		return listBoxSongs.getSelectedIndex();
	}

	@Override
	public HasKeyUpHandlers getSearchTextBox() {
		return textBoxSearch;
	}

	@Override
	public HasValue<String> getSearchText() {
		return textBoxSearch;
	}

	@Override
	public HasClickHandlers getCreateButton() {
		return buttonCreate;
	}

	@Override
	public void setSelectedIndex(int index) {
		listBoxSongs.setSelectedIndex(index);
		DomEvent.fireNativeEvent(Document.get().createChangeEvent(), listBoxSongs);
	}
}
