import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TrelloTests {

    private String boardId;
    private String listId;
    private String cardId;


    @Test(priority=1)
    public void checkCreateBoard() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

        Board board = new Board();
        String name  = "New board";

        Board createBoard = retrofitBuilder.getTrelloApi().createBoard(board, name).execute().body();

        boardId = createBoard.getId();
        Assert.assertEquals(createBoard.getName(), name);
    }

    @Test(priority=2)
    public void checkCreateList() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

        List list = new List();
        String name  = "New list";

        List createList = retrofitBuilder.getTrelloApi().createList(name, boardId, list.getKey(), list.getToken()).execute().body();

        listId = createList.getId();
        Assert.assertEquals(createList.getName(), name);
    }

    @Test(priority=3)
    public void checkCreateCard() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

        Card card = new Card();
        String name  = "New Card";

        Card createCard = retrofitBuilder.getTrelloApi().createCard(name, listId, card.getKey(), card.getToken()).execute().body();

        cardId = createCard.getId();
        Assert.assertEquals(createCard.getName(), name);
    }

    @Test(priority=4)
    public void checkUpdateCard() throws IOException{
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Card card = new Card();

        String updatedDesc = "Updated description";
        card.setDesc(updatedDesc);

        Card updatedCard = retrofitBuilder.getTrelloApi().updateCard(card, cardId).execute().body();

        Assert.assertEquals(updatedCard.getDesc(), updatedDesc);
    }

    @Test(priority=5)
    public void checkGetCard() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

        Card card = new Card();

        Card receivedCard = retrofitBuilder.getTrelloApi().getCard(cardId, card.getKey(), card.getToken()).execute().body();

        Assert.assertEquals(receivedCard.getIdList(), listId);
    }

    @Test(priority=6)
    public void checkDeleteCard() throws IOException{
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Card card = new Card();

        int code = retrofitBuilder.getTrelloApi()
                .deleteCard(cardId, card.getKey(), card.getToken()).execute().code();

        Assert.assertEquals(code, 200);
    }

    @Test(priority=7)
    public void checkGetCardAfterDelete() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

        Card card = new Card();

        int code = retrofitBuilder.getTrelloApi()
                .getCard(cardId, card.getKey(), card.getToken()).execute().code();

        Assert.assertEquals(code, 404);
    }

    @Test(priority=8)
    public void checkUpdateBoard() throws IOException{
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Board board = new Board();
        String updatedName = "Updated board name";
        board.setName(updatedName);

        Board updatedBoard = retrofitBuilder.getTrelloApi().updateBoard(board, boardId).execute().body();

        Assert.assertEquals(updatedBoard.getName(), updatedName);
    }

    @Test(priority=9)
    public void checkDeleteBoard() throws IOException{
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Board board = new Board();

        int code = retrofitBuilder.getTrelloApi()
                .deleteBoard(boardId, board.getKey(), board.getToken()).execute().code();

        Assert.assertEquals(code, 200);
    }
}
