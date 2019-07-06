import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrumpPost {

	public String source;

	public String text;
	public String created_at;
	public int retweet_count;
	public int favorite_count;
	public boolean is_retweet;
	public String id_str;

	public TrumpPost() {

	}

	@Override
	public String toString() {
		return "TrumpPosts [source=" + source + ", text=" + text + ", created_at=" + created_at + ", retweet_count="
				+ retweet_count + ", favorite_count=" + favorite_count + ", is_retweet=" + is_retweet + ", id_str="
				+ id_str + "]";
	}

	/**
	 * Parses a provided JSON file of Trump Posts into a List of TrumpPost instances
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static List<TrumpPost> getPostsFromFile() throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper om = new ObjectMapper();
		List<TrumpPost> list = om.readValue(new File("trump.json"), new TypeReference<List<TrumpPost>>() {
		});

		return list;
	}

	/**
	 * Rerieves the number of Trump Posts needed starting at index 0 up to the index specified
	 * @param index
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static List<TrumpPost> getNumberOfTrumpPostsasList(int index)
			throws JsonParseException, JsonMappingException, IOException {
		return getPostsFromFile().subList(0, index);
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		List<TrumpPost> list = TrumpPost.getPostsFromFile();
		for (TrumpPost post : list) {
			System.out.println(post);
		}
	}
}
