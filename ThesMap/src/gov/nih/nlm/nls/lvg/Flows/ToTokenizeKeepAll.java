package gov.nih.nlm.nls.lvg.Flows;
import java.util.*;
import gov.nih.nlm.nls.lvg.Lib.*;
/*****************************************************************************
* This class breaks up a term into tokens by delimiters (tokenize).  
* Delimiters include space, tab, and all punctuations.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @see <a href="../../../../../../../designDoc/UDF/flow/tokenizeKeepAll.html">
* Design Document </a>
*
* @version    V-2013
****************************************************************************/
public class ToTokenizeKeepAll extends Transformation implements Cloneable
{
    // public methods
    /**
    * Performs the mutation of this flow component.
    *
    * @param   in   a LexItem as the input for this flow component
    * @param   detailsFlag   a boolean flag for processing details information
    * @param   mutateFlag   a boolean flag for processing mutate information
    *
    * @return  Vector<LexItem> - results from this flow component
    * of LexItems
    */
    public static Vector<LexItem> Mutate(LexItem in, boolean detailsFlag, 
        boolean mutateFlag)
    {
        // mutate the term
        Vector<String> termList = GetToken(in.GetSourceTerm());
        // update target LexItem
        Vector<LexItem> out = new Vector<LexItem>();
        for(int i = 0; i < termList.size(); i++)
        {
            // details & mutate
            String details = null;
            String mutate = null;
            if(detailsFlag == true)
            {
                details = INFO;
            }
            if(mutateFlag == true)
            {
                mutate = Transformation.NO_MUTATE_INFO;
            }
            String term = termList.elementAt(i);
            LexItem temp = UpdateLexItem(in, term, Flow.TOKENIZE_KEEP_ALL, 
                Category.ALL_BIT_VALUE, Inflection.ALL_BIT_VALUE, 
                details, mutate); 
            out.addElement(temp);
        }
        return out;
    }
    /**
    * A unit test driver for this flow component.
    */
    public static void main(String[] args)
    {
        String testStr = GetTestStr(args, "The Club-Foot");    // input String
        // Mutate
        LexItem in = new LexItem(testStr);
        Vector<LexItem> outs = ToTokenizeKeepAll.Mutate(in, true, true);
        PrintResults(in, outs);     // print out results
    }
    // private method
    private static Vector<String> GetToken(String inStr)
    {
        // init all delimiters
        String delim = " \t-({[)}]_!@#%&*\\:;\"',.?/~+=|<>$`^";
        StringTokenizer buf = new StringTokenizer(inStr, delim, true);
        Vector<String> out = new Vector<String>();
        String last = new String();
        while(buf.hasMoreTokens() == true)
        {
            String cur = buf.nextToken();
            // combine " " together
            if((cur.equals(" ") == true)
            && (last.equals(" ") == true))
            {
                String temp = out.lastElement() + " ";
                out.setElementAt(temp, out.size()-1);
            }
            else
            {
                out.addElement(cur);
            }
            last = cur;
        }
        return out;
    }
    // data members
    private static final String INFO = "Tokenize keep All";
}
