Coq Proof Tactic Cheat Sheet
============================

Context Manipulation
--------------------

__intro/revert__ - shift goal premises to/from context

__rename__ - rename an assumption in the context

__clear__ - clear an assumption from the context

__assert__ - add an assumption to the context (proving it first)

Applying Theorem and Assumptions
--------------------------------

__assumption__ - goal is equivalent to one of the assumptions.

_exact_ - specify a assumption

__apply__ - use theorem A-\>B to reduce goal B to subgoal A or convert
assumption A to assumption B.
_apply is used for implication, and rewrite is used for equity._

Term Simplification
-------------------

__simpl__ - expand definitions until no more progress is possible.

__unfold__ - expand an identifier to its definitions.

__fold__ - contract an expansion back to its identifier.

Dealing with Equalities
-----------------------

__reflexivity__ - goal is an equality of two equivalent expression.

__symmetry__ - swap goal "e1=e2" to "e2=e1".

__transitivity__ - reduce goal "e1=e2" to subgoals "e1=e" and "e=e2".

__rewrite__ - use assumption "e1=e2" to substitute e1 with e2 (or vice versa).

__subst__ - use and clear assumption "v=e" by replacing all v's with e.

__injection__ - from equality of structures, infer equality of substructures.

__remember__ - introduce a new variable that names a subexpression.

Logical Operators (and/or/exists/forall)
----------------------------------------

__split__ - prove goal A/\B by proving subgoals A and B.

__left/right__ - prove A\/B by proving A (left) or proving B (right).

__exists__ - prove an existential goal by supplying a witness.

__destruct__ - decompose an and/or/exists assumption, or a pair variable.

__specialize__ - instantiate a forall assumption.

Case Distinction and Induction
------------------------------

__destruct__ - introduce one case for each of a term's possible constructors.

__induction__ - same as destruct, but generate an inductive hypothesis.

__inversion__ - perform a case-distinction on an inductive proof object.

Negation and Contradiction
--------------------------

__discriminate__ - solve a goal by recognizing an impossible equality
assumption.

__exfalso__ - solve a goal by proving that False is provable.
