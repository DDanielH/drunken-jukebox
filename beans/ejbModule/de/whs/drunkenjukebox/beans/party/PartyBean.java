package de.whs.drunkenjukebox.beans.party;

import java.util.Calendar;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.whs.drunkenjukebox.common.Util;
import de.whs.drunkenjukebox.model.DIValue;
import de.whs.drunkenjukebox.model.Party;
import de.whs.drunkenjukebox.model.PartyPeople;
import de.whs.drunkenjukebox.model.Playlist;
import de.whs.drunkenjukebox.model.PlaylistEntry;
import de.whs.drunkenjukebox.model.Song;
import de.whs.drunkenjukebox.model.Vote;

/**
 * Session Bean implementation class PartyBean
 */
@Stateless
public class PartyBean implements IPartyRemote {
	@PersistenceContext
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public PartyBean() {
    }

	@Override
	public Playlist getPlaylist() {
		Party currentParty = Util.getCurrentParty(em);
		if (currentParty == null)
			throw new RuntimeException("No current party");
		return currentParty.getPlaylist();
	}

	@Override
	public Song getCurrentSong() {
		Party currentParty = Util.getCurrentParty(em);
		if (currentParty == null)
			throw new RuntimeException("No current party");
		return currentParty.getCurrentSong();
	}

	@Override
	public PartyPeople registerPartyPeople() {
		PartyPeople people = new PartyPeople();
		em.persist(people);
		return people;
	}

	@Override
	public Collection<Vote> getVotes(int partyPeopleId) {
		PartyPeople people = em.find(PartyPeople.class, partyPeopleId);
		return people.getVotes();
	}

	@Override
	public Collection<DIValue> getDiValues(int partyPeopleId) {
		PartyPeople people = em.find(PartyPeople.class, partyPeopleId);
		return people.getDiValues();
	}

	@Override
	public void vote(int partyPeopleId, int songId, boolean up) {
		PartyPeople people = em.find(PartyPeople.class, partyPeopleId);
		Song votedSong = em.find(Song.class, songId);
		
		Party current = Util.getCurrentParty(em);
		PlaylistEntry entry = current.getPlaylist().findSong(votedSong);
		if (entry == null)
			throw new RuntimeException("Voted for song which is not in playlist");
		entry.setVoteCount(entry.getVoteCount() + (up ? 1 : -1));
		
		Vote vote = new Vote();
		vote.setTimestamp(Calendar.getInstance());
		vote.setUpOrDown(up);
		vote.setSong(votedSong);
		people.getVotes().add(vote);
	}

	@Override
	public void sendDi(int partyPeopleId, int di) {
		PartyPeople people = em.find(PartyPeople.class, partyPeopleId);
		DIValue diValue = new DIValue();
		diValue.setDiValue(di);
		diValue.setTimestamp(Calendar.getInstance());
		
		people.setCurrentDI(di);
		people.getDiValues().add(diValue);
	}

}
