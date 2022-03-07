<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Entity\TypeReclamation;
use App\Form\ReclamationType;
use App\Form\TypeReclamationType;
use App\Repository\ReclamationRepository;
use App\Repository\TypeReclamationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
/**
 * @Route("/back")
 */
class BackController extends AbstractController
{


    /**
     * @Route("/AfficheTypeReclamation", name="AfficheTypeReclamation")
     * @param ReclamationRepository $repo
     * @return Response
     */
    public function Affiche(TypeReclamationRepository $repo)
    {

        $typereclamation = $repo->findAll();
        return $this->render('back/index.html.twig',
            ['typereclamation' => $typereclamation]);
    }


    /**
     * @Route("AddTypeReclamation", name="AddTypeReclamation")
     * @return Response
     */

    function Ajouter(Request $request)
    {
        $typereclamation = new TypeReclamation();

        $form = $this->createForm(TypeReclamationType::class, $typereclamation);
        $form->add('ajouter', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($typereclamation);
            $em->flush();
            return $this->redirectToRoute('AfficheTypeReclamation');
        }
        return $this->render('back/AddTypeReclamation.html.twig', [
            'form' => $form->createView(),
        ]);

    }


    /**
     * @Route("UpdateTypeReclamation/{id}",name="UpdateTypeReclamation")
     */
    function Update(TypeReclamationRepository $repo, $id,Request $request){
        $typereclamation=$repo->find($id);
        $form=$this->createForm(TypeReclamationType::class,$typereclamation);
        $form->add('Update', SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('AfficheTypeReclamation');
        }
        return $this->render('back/UpdateTypeReclamation.html.twig',[
            'form'=>$form->createView()]);

    }


    /**
     * @Route("deleteTypeReclamation/{id}",name="deleteTypeReclamation")
     */
    function deleteS($id, TypeReclamationRepository $repo){
        $typereclamation=$repo->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($typereclamation);
        $em->flush();
        return $this->redirectToRoute('AfficheTypeReclamation');
    }



    /**
     * @Route("AfficheReclamation", name="AfficheR")
     * @param ReclamationRepository $repo
     * @return Response
     */
    public function AfficheR(ReclamationRepository $repo)
    {

        $reclamation = $repo->findAll();
        return $this->render('back/AfficheR.html.twig',
            ['reclamation' => $reclamation]);
    }


    /**
     * @Route("deleteR/{id}",name="deleteR")
     */
    function deleteR($id, ReclamationRepository $repo){
        $reclamation=$repo->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($reclamation);
        $em->flush();
        return $this->redirectToRoute('AfficheR');
    }

    /**
     * @Route("verifyR/{id}",name="verifyR")
     */
    function verifyR($id, ReclamationRepository $repo){
        $reclamation=$repo->find($id);
        $reclamation->setStatut('Finish');
        $em=$this->getDoctrine()->getManager();
        $em->flush();
        return $this->redirectToRoute('AfficheR');
    }



}
