import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Put your name here
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        int result = 0;

        /*
         * first check this root of the subtree is an operator
         */
        if (exp.numberOfChildren() != 0) {

            /*
             * Because the expression is a binary tree, we can assume it has two
             * children. Then I can create two subtrees and trust the contract
             * that it will return the right answer.
             */
            XMLTree subTree1 = exp.child(0);
            XMLTree subTree2 = exp.child(1);
            int result1 = evaluate(subTree1);
            int result2 = evaluate(subTree2);

            /*
             * Now the method returned the right answer from all the subtrees,
             * the only thing I need to do is evaluate the root operator of the
             * tree
             */
            if (exp.label().equals("plus")) {
                result = result1 + result2;

            } else if (exp.label().equals("minus")) {
                result = result1 - result2;

            } else if (exp.label().equals("times")) {
                result = result1 * result2;

            } else if (exp.label().equals("divide")) {
                result = result1 / result2;
            }

            /*
             * The else statement will handle the smallest case. The root has no
             * child, then just return the value contains in the attribute
             * called "value".
             */
        } else {
            String value = exp.attributeValue("value");
            return Integer.parseInt(value);
        }
        /*
         * return the result
         */
        return result;

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
