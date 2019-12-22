package cn.ma.cei.finalizer;

import org.junit.Test;

import java.util.List;

public class TestDependence {
    class TestNode implements IDependenceNode {
        private String name;

        public TestNode(String name) {
            this.name = name;
        }


        @Override
        public String getIdentifier() {
            return name;
        }
    }

    @Test
    public void test() {
        TestNode n1 = new TestNode("n1");
        TestNode n2 = new TestNode("n2");
        TestNode n3 = new TestNode("n3");
        TestNode n4 = new TestNode("n4");
        TestNode n5 = new TestNode("n5");
        TestNode n6 = new TestNode("n6");

        Dependence<TestNode> d = new Dependence<>();
        d.addNode(n1, n2);
        d.addNode(n2, n3);
        d.addNode(n4, n2);
        d.addNode(n5, n2);
        d.addNode(n6, n1);

        List<TestNode> res = d.decision();
        System.out.println(res);

    }
}
