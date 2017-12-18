package sample;

import java.util.ArrayList;
import java.util.List;

public abstract class ListCard {

    public static List<Card> InitialiseListCard(int number)
    {
        List<Card> result = new ArrayList<>();
        for(int i = 0; i < number;i++)
        {
            result.add(new DefaultCard());
        }
        return result;
    }

    public static int FindIndex(int index)
    {
        return SequenceNumberMiddle10(index);
    }

    //To get the right index of the card to add. Starting from the middle
    private static int SequenceNumberMiddle10(int index)
    {
        int u0 = 5;
        double t = Math.pow(-1, index);
        double i = Math.ceil((double)index/2);
        int result = (int)(u0 + t * i);
        return result;
    }

    public static List<Integer> EmptyIndex(List<Card> list)
    {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < list.size();i++)
        {
            int index = SequenceNumberMiddle10(i);
            if(list.get(index).equals(Card.DEFAULT_CARD))
            {
                result.add(index);
            }
        }
        return result;
    }

    public static int NextEmptyIndex(List<Card> list)
    {
        List<Integer> resultList = EmptyIndex(list);
        if(resultList.size() > 0)
        {
            return resultList.get(0);
        }
        else
        {
            return -1;
        }
    }

    public static List<Integer> FillIndex(List<Card> list)
    {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < list.size();i++)
        {
            int index = SequenceNumberMiddle10(i);
            if(!list.get(index).equals(Card.DEFAULT_CARD))
            {
                result.add(index);
            }
        }
        return result;
    }

    public static int NextFillIndex(List<Card> list)
    {
        List<Integer> resultList = FillIndex(list);
        if(resultList.size() > 0)
        {
            return resultList.get(0);
        }
        else
        {
            return -1;
        }
    }

    public static int RandomFillIndex(List<Card> list)
    {
        List<Integer> resultList = FillIndex(list);
        //Random r = new
        if(resultList.size() > 0)
        {
            return resultList.get(0);
        }
        else
        {
            return -1;
        }
    }
}
