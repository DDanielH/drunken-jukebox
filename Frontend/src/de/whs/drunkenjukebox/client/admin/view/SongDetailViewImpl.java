package de.whs.drunkenjukebox.client.admin.view;

import java.util.Arrays;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.whs.drunkenjukebox.client.admin.InputBox;
import de.whs.drunkenjukebox.resources.AppResources;
import de.whs.drunkenjukebox.resources.AppResources.AdminStyle;
import de.whs.drunkenjukebox.shared.Song;
import de.whs.drunkenjukebox.shared.SongSourceType;

public class SongDetailViewImpl extends Composite implements SongDetailView {

	private final InputBox interpret;
	private final InputBox title;
	private final InputBox genre;
	private final InputBox length;
	private final TextBox songSource = new TextBox();
	
	private final Button buttonSave = new Button("Speichern");
	private final Button buttonRemove = new Button("Entfernen");
	
	private final RadioButton radioYoutube = new RadioButton("SongSource", "YouTube");
	private final RadioButton radioLocal = new RadioButton("SongSource", "Lokal");
	private AdminStyle style;

	public SongDetailViewImpl(AppResources.AdminStyle style) {
		this.style = style;
		VerticalPanel panel = new VerticalPanel();
		panel.addStyleName(style.songDetailView());

		interpret = new InputBox("Interpret", style);
		panel.add(interpret);
		
		title = new InputBox("Titel", style);
		panel.add(title);
		
		genre = new InputBox("Genre", style);
		panel.add(genre);
		
		length = new InputBox("L�nge", style);
		panel.add(length);

		panel.add(getSourcePanel());

		HorizontalPanel buttonPanel = new HorizontalPanel();		
		buttonPanel.add(buttonSave);
		buttonPanel.add(buttonRemove);
		buttonPanel.setCellHorizontalAlignment(buttonRemove, HasHorizontalAlignment.ALIGN_RIGHT);
		buttonPanel.setWidth("100%");
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		panel.add(buttonPanel);

		initWidget(panel);
	}

	public Widget getSourcePanel() {
		FlexTable table = new FlexTable();
		table.getFlexCellFormatter().setRowSpan(0, 1, 2);
		table.setCellSpacing(8);
		table.setWidth("100%");
		
		table.setWidget(0, 0, radioYoutube);
		table.setWidget(1, 0, radioLocal);
		table.setWidget(0, 1, songSource);
		songSource.setWidth("100%");
		
		CaptionPanel cp = new CaptionPanel("Quelle");
		cp.addStyleName(style.songSourcePanel());
		cp.add(table);
		
		return cp;
	}

	@Override
	public void setSong(Song song) {
		title.setText(song.getTitle());
		interpret.setText(song.getInterpret());
		
		String genres = "";
		for (String s : song.getGenres())
			genres += s + ", ";
		genres = genres.substring(0, genres.length() - 2);
		
		genre.setText(genres);
		length.setText(new Integer(song.getDurationInSecs()).toString());
		songSource.setText(song.getSongSource());
		
		if (song.getSongSourceType()==SongSourceType.local)
			radioLocal.setValue(true);
		else if (song.getSongSourceType()==SongSourceType.youtube)
			radioYoutube.setValue(true);	
	}

	@Override
	public void clear() {
		title.clear();
		interpret.clear();
		genre.clear();
		length.clear();
		songSource.setText("");
		radioLocal.setValue(false);
		radioYoutube.setValue(false);
	}
	
	@Override
	public HasClickHandlers getRemoveButton() {
		return buttonRemove;
	}

	@Override
	public Song getSong() {
		Song song = new Song();
		song.setDurationInSecs(Integer.parseInt(length.getText()));
		String[] genres = genre.getText().split(", ");		
		song.setGenres(Arrays.asList(genres));
		song.setInterpret(interpret.getText());
		song.setTitle(title.getText());
		song.setSongSource(songSource.getText());
		if (radioLocal.getValue())
			song.setSongSourceType(SongSourceType.local);
		else if (radioYoutube.getValue())
			song.setSongSourceType(SongSourceType.youtube);
		return song;
	}

	@Override
	public void setFocusToInterpretBox() {
		interpret.setFocus(true);
	}

	@Override
	public HasClickHandlers getSaveButton() {
		return buttonSave;
	}
}